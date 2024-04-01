import pandas as pd
from dbUtil.mongodb_api import find_by_object_id, find_all_data, find_all_attr, find_data_by_attr_condition
from dbUtil.mysql_api import find_all_rating
from request.request_dto import ChildInfo
from pre.data_preprocess import diet_converter, parse_matrl_name, menu_converter
from datetime import datetime
from custom_exception.exception.custom_http_exception import NotFoundException
"""
@Author: 김회창
"""
last_access_time = None  # 마지막으로 레이팅 데이터에 접근한 시간
CACHE_LIMIT = 30  # 레이팅 데이터 캐싱시간(sec)
N_NEIGHBORS = 10  # 유저의 이웃들의 수
N_RECOMMENDATIONS = 5  # 추천 데이터 개수
menu_objs = {}  # 모든 메뉴에 대한 정보
user_id = ""  # 추천받을 child_id

hazard = []  # 알러지가 있을 경우 먹으면 안되는 음식 키워드
rice_ratings = []  # 밥류 평점 모아놓은 리스트
side_ratings = []  # 반찬류 평점 모+++아놓은 리스트
soup_ratings = []  # 국류 평점 모아놓은 리스트

cached_black_list = []  # 식단 추천시 임시로 검색되지 않게 도와주는 리스트
"""
전역변수 초기화
"""


def _init():
    global user_id
    user_id = None


"""
모든 메뉴 데이터 읽기
"""


def read_menu():
    global menu_objs
    mongo_result = find_all_data("menu")
    for mongo_data in mongo_result:
        menu_objs[mongo_data["menu_id"]] = mongo_data


"""
레이팅 데이터 읽기
:param: exclude_id_list list, 포함시키지 말아야할 menu_id 리스트
:param: exclude_mat_list list, 포함시키지 말아야할 식재료 문자열 리스트
:return: list of tuples : (user_id, menu_id, rating)
"""


def read_ratings(child_id: int, exclude_id_list: list):
    global menu_objs
    global rice_ratings
    global side_ratings
    global soup_ratings
    global hazard
    rice_ratings = []
    side_ratings = []
    soup_ratings = []
    mysql_result = find_all_rating(child_id, exclude_id_list)
    for rating in mysql_result:
        if rating[1] not in menu_objs.keys():
            continue
        is_pass = True
        matrl_name_list = parse_matrl_name(menu_objs[rating[1]]["MATRL_NM"])

        for matrl_name in matrl_name_list:
            if matrl_name in hazard:
                is_pass = False
                break
        if is_pass:
            if menu_objs[rating[1]]["MEAL_CLSF_NM"] == "밥류" or menu_objs[rating[1]]["MEAL_CLSF_NM"] == "죽류":
                rice_ratings.append((
                    int(rating[0]),
                    rating[1],
                    rating[2]
                ))
            elif menu_objs[rating[1]]["MEAL_CLSF_NM"] == "국,탕류":
                soup_ratings.append((
                    int(rating[0]),
                    rating[1],
                    rating[2]
                ))
            else:
                side_ratings.append((
                    int(rating[0]),
                    rating[1],
                    rating[2]
                ))


"""
아이의 알러지 정보를 읽어서 위험한 음식 키워드 들고와서 hazard 리스트 변수에 append함
:param: allergy_name_list list, 해당하는 아이가 보유하고 있는 알러지 이름 리스트
"""


def read_allergy(allergy_name_list):
    global hazard
    hazard = []
    if len(allergy_name_list) == 0:
        return
    exclude_attr = ["_id"]
    allergy_obj_list = find_data_by_attr_condition(allergy_name_list, "allergy_name", is_or=True,
                                                   collection_name="allergy", need_attr=None, exclude_attr=exclude_attr)
    for allergy_obj in allergy_obj_list:
        for hazard_menu in allergy_obj["allergy_hazard"]:
            hazard.append(hazard_menu)


"""
글로벌 변수 child_id 세팅
:param: id int, 아이의 고유번호
"""


def set_user_id(id):
    global user_id
    user_id = id


"""
유저들 사이의 pearson 유사도 측정하기
:param v1: pd.Series, 한 사람이 레이팅을 매긴 메뉴
:param v2: ratings vector, 다른 모든 사람이 레이팅을 매긴 메뉴
:return: float, 유사도 검사 결과값
"""


def pearson_similarity(v1, v2):
    pearson = v1.corr(v2)
    return pearson


"""
유사도를 계산 및 후처리 과정
:param: user_id int, 추천받을 아이의 고유번호
:param: ratings_matrix DataFrame, 모든 아이의 평점 행렬
:return: pd.DataFrame, 추천받을 아이의 기준으로 다른 모든 아이의 메뉴 평점에 근거한 유사도
"""


def compute_similarity(user_id, ratings_matrix):
    ratings_user = ratings_matrix.loc[user_id, :]
    similarities = ratings_matrix.apply(
        lambda row: pearson_similarity(ratings_user, row),
        axis=1
    )
    similarities = similarities.to_frame(name='similarity')

    similarities = similarities.sort_values(by='similarity', ascending=False)

    similarities = similarities.drop(user_id)

    return similarities


"""
유사한 유저의 평가를 기반으로 어떤 사용자의 주어진 아이템의 레이팅을 예측
해당 아이템에 대해 평가를 매긴 가장 높은 유사도 측정값을 가진 N명의 유저를 갖고서
이미 해당 아이템에 대해 평가를 매긴 가장 유사도가 높은 유저들의 평가값의 평균을 리턴한다.
:param item_id: int, 레이팅 예측이 필요한 아이템
:param ratings: pd.DataFrame, rating 행렬
:param similarities: pd.DataFrame, 유사도 행렬
:param N: int, 레이팅 예측을 위해 유사한 N명의 사람을 선별
:return: float, 계산 결과 item_id에 대한 예측되는 레이팅
"""


def predict_rating(item_id, ratings, similarities, N=10):
    users_ratings = ratings.loc[:, item_id]

    most_similar_users_who_rated_item = similarities.loc[~users_ratings.isnull()]

    N_most_similar_users = most_similar_users_who_rated_item.head(N)

    ratings_for_item = ratings.loc[N_most_similar_users.index, item_id]
    return ratings_for_item.mean()


def gen_condition(menu_obj, condition_obj):
    global cached_black_list
    if "CALORIE_QY" in condition_obj:
        if float(menu_obj["CALORIE_QY"]) > float(condition_obj["CALORIE_QY"]):
            return False
    if "CARBOH_QY" in condition_obj:
        if float(menu_obj["CARBOH_QY"]) > float(condition_obj["CARBOH_QY"]):
            return False
    if "PROTEIN_QY" in condition_obj:
        if float(menu_obj["PROTEIN_QY"]) > float(condition_obj["PROTEIN_QY"]):
            return False
    if "CELLU_QY" in condition_obj:
        if float(menu_obj["CELLU_QY"]) > float(condition_obj["CELLU_QY"]):
            return False
    if menu_obj["menu_id"] in cached_black_list:
        return False
    return True


"""
레이팅 데이타를 기반으로 유저에게 N개의 메뉴를 추천해준다.
:param n_neighbors int, 레이팅 예측값을 생성하기 위해 사용되는 이웃의 수
:param n_recomm int, 추천받을 메뉴의 개수
:return: result list[dict], 평점 예측의 결과로 N개의 메뉴를 추천
"""


def recommend(cur_ratings, n_neighbors, n_recomm, condition_obj):
    global menu_objs
    rating_pd = pd.DataFrame(data=cur_ratings, columns=['user_id', 'menu_id', 'rating'])
    rating_pd = rating_pd.pivot(index='user_id', columns='menu_id', values='rating')

    if user_id not in rating_pd.index:
        rating_pd.loc[user_id] = None

    all_items = rating_pd.loc[user_id, :]

    unrated_items = all_items.loc[all_items.isnull()]
    unrated_items = unrated_items.index.to_series(name='item_ids').reset_index(drop=True)
    sim = compute_similarity(user_id, rating_pd)

    predictions = unrated_items.apply(lambda d: predict_rating(d, rating_pd, sim, N=n_neighbors))
    predictions = predictions.sort_values(ascending=False)
    some_list = list()
    # recomm_test = predictions
    # recomm_test = recomm_test.to_frame(name='predicted_rating')
    # recomm_test = recomm_test.rename_axis('u_index')
    # recomm_test = recomm_test.reset_index()
    # recomm_test.u_index.apply(lambda d: some_list.append(menu_objs[unrated_items[int(d)]]))

    recomms = predictions.to_frame(name='predicted_rating')
    recomms = recomms.rename_axis('u_index')
    recomms = recomms.reset_index()
    # recomms = recomms.head(n_recomm)
    recomms.u_index.apply(lambda d: some_list.append(unrated_items[int(d)]))
    filtered_recomms = list(filter(lambda x: gen_condition(menu_objs[x], condition_obj), some_list))
    if len(filtered_recomms) == 0:
        data = some_list[:n_recomm]
        filtered_recomms.append(data[0])
    return filtered_recomms[:n_recomm]


"""
추천 알고리즘 돌리기 전 초기화 단계 실행하는 함수
"""


def _init_process(request: ChildInfo):
    global last_access_time
    global CACHE_LIMIT
    global cached_black_list
    global user_id
    cached_black_list = []
    read_allergy(request.allergyList)
    if (last_access_time is None) or (datetime.now() - last_access_time).total_seconds() >= CACHE_LIMIT:
        _init()
        last_access_time = datetime.now()
        read_menu()
    read_ratings(request.childId, request.cacheList)
    user_id = request.childId


"""
하나의 밥류 메뉴를 추천해주는 함수
:param: condition_obj dict, 현재 사용 가능한 칼로리 및 영양소 수치, 이 수치를 기반으로 적절한 밥을 추천함
:return: list[dict], recommend 함수를 통해 추천된 밥류 menu dict를 리턴
"""


def recommend_rice(condition_obj):
    global rice_ratings
    return recommend(rice_ratings, n_neighbors=N_NEIGHBORS, n_recomm=1, condition_obj=condition_obj)


"""
하나의 국류 메뉴를 추천해주는 함수
:param: condition_obj dict, 현재 사용 가능한 칼로리 및 영양소 수치, 이 수치를 기반으로 적절한 국을 추천함
:return: list[dict], recommend 함수를 통해 추천된 국류 menu dict를 리턴
"""


def recommend_soup(condition_obj):
    global soup_ratings
    return recommend(soup_ratings, n_neighbors=N_NEIGHBORS, n_recomm=1, condition_obj=condition_obj)


"""
하나의 반찬류 메뉴를 추천해주는 함수
:param: condition_obj dict, 현재 사용 가능한 칼로리 및 영양소 수치, 이 수치를 기반으로 적절한 반찬을 추천함
:return: list[dict], recommend 함수를 통해 추천된 반찬류 menu dict를 리턴
"""


def recommend_side(condition_obj):
    global side_ratings
    return recommend(side_ratings, n_neighbors=N_NEIGHBORS, n_recomm=1, condition_obj=condition_obj)



"""
하나의 식단을 추천해주는 함수
:return: result list[dict], 하나의 식단에 밥류 1개, 반찬류 2개, 국 1개의 총 4개 메뉴 정보를 추가한 리스트 리턴
"""


def one_diet_recommend(request: ChildInfo) -> list[dict]:
    diet = []
    global cached_black_list
    require_protein = float(request.need.protein)
    require_cellulose = float(request.need.cellulose)
    require_carbohydrate = float(request.need.carbohydrate)
    require_calories = float(request.need.calories)
    condition_obj = {
        "CALORIE_QY": require_calories,
        "CARBOH_QY": require_carbohydrate
    }
    result = recommend_rice(condition_obj)

    require_calories -= float(menu_objs[result[0]]["CALORIE_QY"])
    diet.append(menu_objs[result[0]])
    cached_black_list.append(result[0])
    # for i in reversed(range(len(rice_ratings))):
    #     if rice_ratings[i][1] == result[0]:
    #         del rice_ratings[i]
    condition_obj = {
        "CALORIE_QY": require_calories,
        "PROTEIN_QY": require_protein,
        "CELLU_QY": require_cellulose,
    }
    result = recommend_soup(condition_obj)

    diet.append(menu_objs[result[0]])
    cached_black_list.append(result[0])
    # for i in reversed(range(len(soup_ratings))):
    #     if soup_ratings[i][1] == result[0]:
    #         del soup_ratings[i]
    require_calories -= float(menu_objs[result[0]]["CALORIE_QY"])
    require_protein -= float(menu_objs[result[0]]["PROTEIN_QY"])
    require_cellulose -= float(menu_objs[result[0]]["CELLU_QY"])

    for __ in range(0, 2):
        condition_obj = {
            "CALORIE_QY": require_calories,
            "PROTEIN_QY": require_protein,
            "CELLU_QY": require_cellulose,
        }
        result = recommend_side(condition_obj)
        diet.append(menu_objs[result[0]])
        cached_black_list.append(result[0])
        # for i in reversed(range(len(side_ratings))):
        #     if side_ratings[i][1] == result[0]:
        #         del side_ratings[i]
        require_calories -= float(menu_objs[result[0]]["CALORIE_QY"])
        require_protein -= float(menu_objs[result[0]]["PROTEIN_QY"])
        require_cellulose -= float(menu_objs[result[0]]["CELLU_QY"])
    return diet


"""
초창기 7가지 식단을 UBCF알고리즘 기반으로 추천해주는 함수
:param: request ChildInfo, 아이의 정보 및 가까운 시일내에 추천받았던 메뉴를 제외시키기 위해 클라이언트로 부터 넘어온 정보
:return: list[list[dict]], 클라이언트로 부터 받은 정보를 바탕으로 7개의 식단을 추천한 결과를 리턴 
"""


def init_recommendations(request: ChildInfo) -> list[list[dict]]:
    _init_process(request)

    result = []
    for __ in range(7):
        diet = diet_converter(one_diet_recommend(request))

        result.append(diet)
    return result


"""
단일 식단을 새롭게 추천해주는 함수
:param: request ChildInfo, 아이의 정보 및 가까운 시일내에 추천받았던 메뉴를 제외시키기 위해 클라이언트로 부터 넘어온 정보
:return: list[dict], 클라이언트로 부터 받은 정보를 바탕으로 1개의 식단을 추천한 결과를 리턴 
"""


def one_recommend(request: ChildInfo) -> list[dict]:
    _init_process(request)
    return diet_converter(one_diet_recommend(request))


"""
단일 메뉴를 새롭게 추천해주는 함수
:param: request ChildInfo, 아이의 정보 및 가까운 시일내에 추천받았던 메뉴를 제외시키기 위해 클라이언트로 부터 넘어온 정보
:return: dict, 클라이언트로 부터 받은 정보를 바탕으로 1개의 메뉴를 추천한 결과를 리턴 
"""


def menu_recommend(request: ChildInfo) -> dict:
    _init_process(request)
    global menu_objs
    condition_obj = dict()
    sum_of_cal = 0
    sum_of_protein = 0
    sum_of_cellulose = 0
    for menu_id in request.currentMenuIdOfDiet:
        if menu_id in menu_objs:
            sum_of_protein += float(menu_objs[menu_id]["PROTEIN_QY"])
            sum_of_cal += int(float(menu_objs[menu_id]["CALORIE_QY"]))
            sum_of_cellulose += float(menu_objs[menu_id]["CELLU_QY"])
        else:
            result = find_by_object_id("menu", menu_id)
            if len(result) == 0:
                raise NotFoundException(f'menu_id에 해당하는 데이터가 없습니다, req: {request}, menu_id={menu_id} not in')
            sum_of_protein += float(result[0]["PROTEIN_QY"])
            sum_of_cal += int(float(result[0]["CALORIE_QY"]))
            sum_of_cellulose += float(result[0]["CELLU_QY"])
    condition_obj["CALORIE_QY"] = request.need.calories - sum_of_cal
    if request.needType == "RICE":
        condition_obj["CARBOH_QY"] = request.need.carbohydrate
        new_menu_id = recommend_rice(condition_obj)[0]
        return menu_converter(menu_objs[new_menu_id])
    condition_obj["PROTEIN_QY"] = request.need.protein - sum_of_protein
    condition_obj["CELLU_QY"] = request.need.cellulose - sum_of_cellulose
    if request.needType == "SOUP":
        new_menu_id = recommend_soup(condition_obj)[0]
        return menu_converter(menu_objs[new_menu_id])
    else:
        new_menu_id = recommend_side(condition_obj)[0]
        return menu_converter(menu_objs[new_menu_id])

