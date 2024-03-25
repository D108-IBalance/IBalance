import pandas as pd
from dbUtil.mongodb_api import find_by_object_id, find_all_data, find_all_attr, find_data_by_attr_condition
from dbUtil.mysql_api import find_all_rating
from request.request_dto import ChildInfo
from pre.data_preprocess import diet_converter, parse_matrl_name

"""
@Author: 김회창
"""

N_NEIGHBORS = 10  # 유저의 이웃들의 수
N_RECOMMENDATIONS = 5  # 추천 데이터 개수
menu_objs = {}  # 모든 메뉴에 대한 정보
user_id = ""  # 추천받을 child_id

hazard = [] # 알러지가 있을 경우 먹으면 안되는 음식 키워드
rice_ratings = []   # 밥류 평점 모아놓은 리스트
side_ratings = []   # 반찬류 평점 모+++아놓은 리스트
soup_ratings = []   # 국류 평점 모아놓은 리스트


"""
전역변수 초기화
"""


def _init():
    global hazard
    global user_id
    global rice_ratings
    global side_ratings
    global soup_ratings
    rice_ratings = []
    side_ratings = []
    soup_ratings = []
    hazard = []
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


def read_ratings(exclude_id_list: list):
    global menu_objs
    global rice_ratings
    global side_ratings
    global soup_ratings
    global hazard
    mysql_result = find_all_rating(exclude_id_list)
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
    if len(allergy_name_list) == 0:
        return
    exclude_attr = ["_id"]
    allergy_obj_list = find_data_by_attr_condition(allergy_name_list, "allergy_name", is_or=True, collection_name="allergy", need_attr=None, exclude_attr=exclude_attr)
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


"""
레이팅 데이타를 기반으로 유저에게 N개의 메뉴를 추천해준다.
:param n_neighbors int, 레이팅 예측값을 생성하기 위해 사용되는 이웃의 수
:param n_recomm int, 추천받을 메뉴의 개수
:return: result list[dict], 평점 예측의 결과로 N개의 메뉴를 추천
"""


def recommend(cur_ratings, n_neighbors=10, n_recomm=5):
    rating_pd = pd.DataFrame(data=cur_ratings, columns=['user_id', 'menu_id', 'rating'])
    rating_pd = rating_pd.pivot(index='user_id', columns='menu_id', values='rating')
    ratings_users = rating_pd.loc[user_id, :]

    all_items = rating_pd.loc[user_id, :]

    unrated_items = all_items.loc[all_items.isnull()]
    unrated_items = unrated_items.index.to_series(name='item_ids').reset_index(drop=True)
    sim = compute_similarity(user_id, rating_pd)
    predictions = unrated_items.apply(lambda d: predict_rating(d, rating_pd, sim, N=n_neighbors))
    predictions = predictions.sort_values(ascending=False)
    recomms = predictions.head(n_recomm)
    recomms = recomms.to_frame(name='predicted_rating')
    recomms = recomms.rename_axis('u_index')
    recomms = recomms.reset_index()
    recomms['menu_id'] = recomms.u_index.apply(lambda d: unrated_items[int(d)])
    result = []
    recomms.menu_id.apply(lambda id: result.append(menu_objs[id]))
    return result


"""
하나의 식단을 추천해주는 함수
:return: result list[dict], 하나의 식단에 밥류 1개, 반찬류 2개, 국 1개의 총 4개 메뉴 정보를 추가한 리스트 리턴
"""


def one_diet_recommend():
    diet = []
    global soup_ratings
    global rice_ratings
    global side_ratings
    result = recommend(rice_ratings, n_neighbors=N_NEIGHBORS, n_recomm=1)
    diet.append(result[0])
    for i in reversed(range(len(rice_ratings))):
        if rice_ratings[i][1] == result[0]["menu_id"]:
            del rice_ratings[i]

    result = recommend(soup_ratings, n_neighbors=N_NEIGHBORS, n_recomm=1)
    diet.append(result[0])
    for i in reversed(range(len(soup_ratings))):
        if soup_ratings[i][1] == result[0]["menu_id"]:
            del soup_ratings[i]

    result = recommend(side_ratings, n_neighbors=N_NEIGHBORS, n_recomm=2)
    for side in result:
        diet.append(side)
        for i in reversed(range(len(side_ratings))):
            if side_ratings[i][1] == side["menu_id"]:
                del side_ratings[i]

    return diet


"""
초창기 7가지 식단을 UBCF알고리즘 기반으로 추천해주는 함수
:param: request ChildInfo, 아이의 정보 및 가까운 시일내에 추천받았던 메뉴를 제외시키기 위해 클라이언트로 부터 넘어온 정보
:return: list[list[dict]], 클라이언트로 부터 받은 정보를 바탕으로 7개의 식단을 추천한 결과를 리턴 
"""


def init_recommendations(request: ChildInfo):
    _init()
    set_user_id(request.childId)
    read_menu()
    read_allergy(request.allergyList)
    read_ratings(request.cacheList)
    result = []
    for __ in range(7):
        diet = diet_converter(one_diet_recommend())
        result.append(diet)
    return result
