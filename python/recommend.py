import pandas as pd
from dbUtil.mongodb_api import find_by_object_id
from dbUtil.mysql_api import find_all

# 유저의 이웃들의 수
N_NEIGHBORS = 10

# 추천 데이터 개수
N_RECOMMENDATIONS = 5
ratings = []

user_id = ""

def _init():
    global ratings
    global movie_names
    global user_id
    ratings = []
    movie_names = []
    user_id = None

def read_ratings():
    """
    레이팅 데이터 읽기
    :param path: 레이팅 데이터 path
    :return: list of tuples : (user_id, menu_id, rating)
    """
    global ratings
    mysql_result = find_all("menu_rating_cp")
    for rating in mysql_result:
        ratings.append((
            int(rating[1]),
            rating[2],
            rating[3]
        ))


def set_user_id(id):
    global user_id
    user_id = id


# 유저들 사이의 유사도 측정하기

def pearson_similarity(v1, v2):
    """
    :param v1: pd.Series
    :param v2: ratings vector
    :return: float
    """
    pearson = v1.corr(v2)
    return pearson


def compute_similarity(user_id, ratings_matrix):
    """
    주어진 사용자와 모든 다른 사용자들 사이의 유사도를 데이터셋을 이용하여 계산한다.
    결과로부터 주어진 사용자에 대한 유사도 값은 제거해야 한다.
    :param user_id:
    :param ratings_matrix:
    :return: pd.Series with user_id's as index, and similarity as series values
    """
    # 유사도 계산에서 재사용 할 유저의 레이팅을 얻는다.
    ratings_user = ratings_matrix.loc[user_id, :]
    similarities = ratings_matrix.apply(
        lambda row: pearson_similarity(ratings_user, row),
        axis=1
    )
    similarities = similarities.to_frame(name='similarity')

    # 주어진 유저와 가장 유사한 사용자들을 찾는다.
    similarities = similarities.sort_values(by='similarity', ascending=False)

    # 본인에 대한 유사도는 제거한다.
    similarities = similarities.drop(user_id)

    return similarities



def predict_rating(item_id, ratings, similarities, N=10):
    # print("들어 오는 데이터 : "+str(item_id))
    """
    유사한 유저의 평가를 기반으로 어떤 사용자의 주어진 아이템의 레이팅을 예측
    해당 아이템에 대해 평가를 매긴 가장 높은 유사도 측정값을 가진 N명의 유저를 갖고서
    이미 해당 아이템에 대해 평가를 매긴 가장 유사도가 높은 유저들의 평가값의 평균을 리턴한다.
    :param item_id: int, 레이팅 예측이 필요한 아이템
    :param ratings: pd.DataFrame
    :param similarities: pd.DataFrame
    :param N: int, 레이팅 예측을 위해 사용되는 N명의 이웃
    :return: float, 주어진 아이템에 대한 예측된 레이팅 값
    """
    # 특정 아이템에 대한 모든 유저의 레이팅을 들고온다.
    users_ratings = ratings.loc[:, item_id]
    # 주어진 아이템에 대한 유저들을 유지시킨다
    # 유사한 유저들 사이에서 해당 아이템에 대한 평가를 내린 유저들만의 데이터로 정제
    most_similar_users_who_rated_item = similarities.loc[~users_ratings.isnull()]
    # 그 중 N명의 유저만을 간추림
    N_most_similar_users = most_similar_users_who_rated_item.head(N)
    # N명의 유저들이 해당 아이템에 매긴 레이팅값을 들고와서
    # 그 평균을 리턴시킴
    ratings_for_item = ratings.loc[N_most_similar_users.index, item_id]
    return ratings_for_item.mean()

def recommend(n_neighbors=10, n_recomm=5):
    """
    레이팅 데이타를 기반으로 유저에게 N개의 영화를 추천해준다.
     1. 해당 유저의 메뉴 레이팅값들을 들고온다.
     2. 유저가 레이팅을 부여하지 않은 메뉴값들을 들고온다.
     3. 유저와 다른 유저들 사이의 유사도를 계산한다.
     4. 다른 유저와의 유사도를 기반으로 하여 해당 유저에 대한 메뉴 레이팅 예측값을 생성한다.
     5. 예측값 중 가장 높은 N개의 메뉴를 찾는다.
    :param user_id: int, 추천받는 사람의 user_id
    :param ratings: pd.DataFrame, user_id와 menu rating값
    :param movie_names: dict key : movie_id, value : menu_name
    :param n_neighbors: int, 레이팅 예측값을 생성하기 위해 사용되는 이웃의 수
    :param n_recomm: int, 추천받을 영화의 개수
    :return: pd.DataFrame with [movie_id, rating, movie_name]
    """
    # Data 형식 바꾸기
    global ratings
    global user_id
    rating_pd = pd.DataFrame(data=ratings, columns=['user_id', 'menu_id', 'rating'])
    rating_pd = rating_pd.pivot(index='user_id', columns='menu_id', values='rating')

    # 해당 유저의 레이팅들을 가져온다.
    ratings_users = rating_pd.loc[user_id, :]

    all_items = rating_pd.loc[user_id, :]

    unrated_items = all_items.loc[all_items.isnull()]
    # item_id값들에서 Series 값들로 인덱싱을 바꾼다.
    unrated_items = unrated_items.index.to_series(name='item_ids').reset_index(drop=True)
    # 유사도를 계산한다.
    sim = compute_similarity(user_id, rating_pd)
    #unrated_items.apply(lambda d: print("d : {} in movie_names : {}, type {}".format(int(d), int(d) in movie_names, type(d))))
    # 해당 유저의 유사도 데이터를 기반으로 보지않은 아이템들에 대한 예측값을 생성한다.
    predictions = unrated_items.apply(lambda d: predict_rating(d, rating_pd, sim, N=n_neighbors))
    predictions = predictions.sort_values(ascending=False)
    # top N개의 아이템들을 추천한다.
    recomms = predictions.head(n_recomm)
    # 결과값을 형식에 맞춘다.
    recomms = recomms.to_frame(name='predicted_rating')
    recomms = recomms.rename_axis('u_index')
    recomms = recomms.reset_index()
    recomms['menu_id'] = recomms.u_index.apply(lambda d: unrated_items[int(d)])
    result = []
    recomms.menu_id.apply(lambda id: result.append(find_by_object_id("menu",id)))
    return result

# recommends = recommend(user_id, ratings, movie_names, n_neighbors=N_NEIGHBORS, n_recomm=N_RECOMMENDATIONS)
# 국 하나 추천
# 밥 하나 추천
# 반찬 두번 추천
# 무조건 밥 국 반찬 순서로 일단 진행


def getRecomm(id, option):
    _init()
    global user_id
    global ratings
    global movie_names
    set_user_id(id)
    read_ratings()
    recommends = recommend(n_neighbors=N_NEIGHBORS, n_recomm=N_RECOMMENDATIONS)
    return recommends
