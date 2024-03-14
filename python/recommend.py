import numpy as np
import pandas as pd
import csv

# 유저의 이웃들의 수
N_NEIGHBORS = 10

# 추천 데이터 개수
N_RECOMMENDATIONS = 5
ratings = []
movie_names = []

user_id = ""

def _init():
    global ratings
    global movie_names
    global user_id
    ratings = []
    movie_names = []
    user_id = None

def read_ratings(mongo_ratings):
    """
    레이팅 데이터 읽기
    :param path: 레이팅 데이터 path
    :return: list of tuples : (user_id, movie_id, rating)
    """
    global ratings
    for rating in mongo_ratings:
        ratings.append((
            int(rating["user_id"]),
            rating["movie_id"],
            rating["rating"]
        ))



def read_names(mongo_movies):
    """
    영화 id와 실제 영화 id에 대응되는 영화 name
    :param path: 영화 데이터 경로
    :return: dictionary key : movie_id, value : title
    """
    data = {}
    for movie in mongo_movies:
        movie_id = int(movie["movie_id"])
        title = movie["title"]
        data[movie_id] = title
    global movie_names
    movie_names = data

def set_user_id(id):
    global user_id
    user_id = id

# ratings = read_ratings(MOVIE_RATINGS_PATH)


# pd.DataFrame()

# data 확인
# print(ratings.head())

# 레이팅 매겨진 영화의 개수
# print(ratings.movie.nunique())

# 레이팅을 매긴 유저의 수
# print(ratings.user.nunique())

# 테스트 목적의 데이터 선정하기
# sample = ratings.sample(random_state=42)
# item_id = sample.movie.values[0]

# 테스트 목적의 user_id와 item_id 출력
# print(user_id, item_id)

# ratings_raw = ratings.copy()
# ratings = ratings.pivot(index='user', columns='movie', values='rating')
# ratings = ratings.astype(float)

# print(ratings.head())

# Movielens 데이터셋은 소윔라해 너무 긴 형식이다.
# 따라서 필요한 데이터만 빼내어 사용할 필요가 있으므로 정제를 한다.

# print(ratings.loc[user_id, :].sample(10))

# 유저들 사이의 유사도 측정하기
"""
레이팅 벡터는 유저들 사이의 유사한지 혹은 유사하지 않은지를 측정하는데 사용된다.
두명의 유저가 같은 영화에 대해 같은 레이팅을 부여했다고 했을 때,
이 두 유저들은 그들의 영화 입맛을 기반으로 유사하다고 볼 수 있다.
그 반대로, 두 유저가 매우 다르게 같은 영화에 대해 평가했다면, 
그 유저들은 해당 영화에 대해 매우 다른 입맛을 가지고 있다.

유저 기반 협업 필터링은 다른 누군가에게 영화를 추천하기 위해 
매우 유사한 입맛을 가진 유저를 찾는데 초점을 맞춘다.

우리는 Pearson 상관관계를 사용하여 두 레이팅 벡터 사이에서 유사도를 측정한다.
Pearson 상관관계는 -1~+1 사이의 값을 가진다. 
0은 상관관계가 없다는것을 의미한다.
1은 매우 유사하단것이고, -1은 매우 유사하지 않다는 것이다.

데이터셋에서 어떤 사람과 모든 다른 사람들과의 유사도를 계산하자.
"""


def pearson_similarity(v1, v2):
    """
    :param v1: pd.Series
    :param v2: ratings vector
    :return: float
    """
    pearson = v1.corr(v2)
    return pearson


# pearson 유사도 테스트
# print(pearson_similarity(pd.Series([1,2,3,4,5, np.NaN]), pd.Series([2,1,4,5,np.NaN, 1])))

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


# similarities = compute_similarity(user_id, ratings)
# print(similarities.head())

"""
사용자 유사도는 어떤 유저가 레이팅을 매기지않은 영화에 대한 레이팅을 예측한다.
레이팅 예측을 하는 방법은 아래와 같다.
    1. 주어진 영화를 평가한 사용자와 가장 유사한 N명의 이웃을 찾는다.
    2. 그 유저의 N명의 이웃에 대한 영화 레이팅의 평균을 낸다.
"""


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

"""
이제 어떤 유저가 안본 영화에 대하여 유사도를 매기기 위해
해당 유저와 유사한 유저들 중 해당 영화에 레이팅을 매긴 유저들만을 간추리고
그 유저들이 매긴 해당 영화의 레이팅값 평균을 가지고 예측하였다.

어떤 유저가 레이팅을 매기지않은 영화를 찾는다.
이러한 레이팅이 매겨지지않은 영화들에 대하여 사용자가 부여할 레이팅 값을 예측합니다.
가장 높은 레이팅 값들을 가진 N개의 영화를 찾는다.
"""


def recommend(user_id, ratings, movie_names, n_neighbors=10, n_recomm=5):
    """
    레이팅 데이타를 기반으로 유저에게 N개의 영화를 추천해준다.
     1. 해당 유저의 영화 레이팅값들을 들고온다.
     2. 유저가 레이팅을 부여하지 않은 영화값들을 들고온다.
     3. 유저와 다른 유저들 사이의 유사도를 계산한다.
     4. 다른 유저와의 유사도를 기반으로 하여 해당 유저에 대한 영화 레이팅 예측값을 생성한다.
     5. 예측값 중 가장 높은 N개의 영화를 찾는다.
    :param user_id: int, 추천받는 사람의 user_id
    :param ratings: pd.DataFrame, user_id와 movie rating값
    :param movie_names: dict key : movie_id, value : movie_name
    :param n_neighbors: int, 레이팅 예측값을 생성하기 위해 사용되는 이웃의 수
    :param n_recomm: int, 추천받을 영화의 개수
    :return: pd.DataFrame with [movie_id, rating, movie_name]
    """
    # Data 형식 바꾸기
    ratings = pd.DataFrame(data=ratings, columns=['user', 'movie', 'rating'])
    print("pd ratings : ")
    print(ratings)

    ratings = ratings.pivot(index='user', columns='movie', values='rating')
    ratings = ratings.astype(float)

    # 해당 유저의 레이팅들을 가져온다.
    ratings_users = ratings.loc[user_id, :]

    all_items = ratings.loc[user_id, :]
    print(f'user_id : {user_id} 가 레이팅을 매긴 모든 영화')

    unrated_items = all_items.loc[all_items.isnull()]

    # item_id값들에서 Series 값들로 인덱싱을 바꾼다.
    unrated_items = unrated_items.index.to_series(name='item_ids').reset_index(drop=True)
    print('User {} has {} unrated items'.format(user_id, len(unrated_items)))
    # print("unrated_ items : ")
    print(unrated_items)
    # unrated_items.map(lambda x: print(x in movie_names))
    # print(movie_names[1818])
    # 유사도를 계산한다.
    sim = compute_similarity(user_id, ratings)
    #unrated_items.apply(lambda d: print("d : {} in movie_names : {}, type {}".format(int(d), int(d) in movie_names, type(d))))
    # 해당 유저의 유사도 데이터를 기반으로 보지않은 아이템들에 대한 예측값을 생성한다.
    print(sim.head())
    predictions = unrated_items.apply(lambda d: predict_rating(d, ratings, sim, N=n_neighbors))
    print(predictions.head())
    predictions = predictions.sort_values(ascending=False)
    # top N개의 아이템들을 추천한다.
    recomms = predictions.head(n_recomm)
    print("recomm : ")
    print(recomms)
    # 결과값을 형식에 맞춘다.
    recomms = recomms.to_frame(name='predicted_rating')
    recomms = recomms.rename_axis('u_index')
    recomms = recomms.reset_index()
    recomms['movie_id'] = recomms.u_index.apply(lambda d: unrated_items[int(d)])
    print(recomms['movie_id'])
    recomms['name'] = recomms.movie_id.apply(lambda d: movie_names[int(d)])
    recomms = recomms.reset_index()

    print("recomm movie_id")
    print(recomms['movie_id'])
    print("recomm movie_names")
    print(recomms['name'])
    data = []
    for i in range(0, n_recomm):
        frag = {}
        frag['movie_id'] = recomms['movie_id'][i]
        frag['movie_name'] = recomms['name'][i]
        data.append(frag)
    return data

# recommends = recommend(user_id, ratings, movie_names, n_neighbors=N_NEIGHBORS, n_recomm=N_RECOMMENDATIONS)

def getRecomm(client, id : int):
    _init()
    global user_id
    global ratings
    global movie_names
    set_user_id(id)
    test_db = client.get_database("test")
    movie_cols = test_db.get_collection("movies")
    rating_cols = test_db.get_collection("ratings")
    mongo_ratings = rating_cols.find()
    mongo_movies = movie_cols.find()
    read_ratings(mongo_ratings)
    read_names(mongo_movies)
    recommends = recommend(user_id, ratings, movie_names, n_neighbors=N_NEIGHBORS, n_recomm=N_RECOMMENDATIONS)
    return recommends

## must copy    

# def recommend_first_init(mysql_client, mongo_client, req_user_id):
#     get_all_user_data()
#     return
#
#
# recommend_first_init()