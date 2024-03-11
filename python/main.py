from typing import Union
from fastapi import FastAPI
from pymongo.mongo_client import MongoClient
from pymongo.server_api import ServerApi
import uvicorn
from pydantic_settings import BaseSettings
from UBCFPractice import read_ratings, read_names, set_user_id, getRecomm
import mysql.connector
import csv



class Settings(BaseSettings):
    MONGO_HOST: str
    MYSQL_HOST: str
    MYSQL_USER: str
    MYSQL_PASSWORD: str
    MYSQL_DATABASE: str

settings = Settings()

app = FastAPI()
# ratings = []
uri = f'{settings.MONGO_HOST}'
client = MongoClient(uri, server_api=ServerApi('1'))
mydb = mysql.connector.connect(
    host=settings.MYSQL_HOST,
    user=settings.MYSQL_USER,
    password=settings.MYSQL_PASSWORD,
    database=settings.MYSQL_DATABASE
)

if mydb.is_connected():
    print("mysql is connected!!!")



def test():
    cur = mydb.cursor()
    sql = '''SELECT * FROM rating WHERE 1=1'''
    cur.execute(sql)
    result = cur.fetchall()
    for res in result:
        print(res)
# mysql 접속 테스트, 아래 주석 제거할 것
# test()

# db = client.get_database("test")
# db.list_collections()
# menu_cols = db.get_collection("menu")

try:
    client.admin.command('ping')
    print("Pinged your deployment. You successfully connected to MongoDB!")
except Exception as e:
    print(e)

@app.get("/hello")
def hello_world(user_id : int | None = None):
    return {"Hello": "World"}


# def _load_movies(csv_path : str):
#     f = open(csv_path, 'r', encoding='utf-8')
#     lines = csv.reader(f)
#     for line in lines:
#         if line[0] == 'userId':
#             continue
#         movie_id = line[0]
#         title = line[1]
#         genre = line[2]
#         movies.append(
#             {
#                 'movie_id' : movie_id,
#                 'title' : title,
#                 'genre' : genre
#             }
#         )
#     f.close()
#
# def _load_ratings(csv_path : str):
#     f = open(csv_path, 'r', encoding='utf-8')
#     lines = csv.reader(f)
#     for line in lines:
#         if line[0] == 'movieId':
#             continue
#         user_id = line[0]
#         movie_id = line[1]
#         rating = line[2]
#         ratings.append({
#             'user_id' : user_id,
#             'movie_id' : movie_id,
#             'rating' : rating
#         })
#     f.close()
# @app.get("/movie/push")
# def movie_insert():
#     database = client["test"]
#     movies_collection = database["movies"]
#     _load_movies("movies.csv")
#     movies_collection.insert_many(movies)
#
# @app.get("/rating/push")
# def rating_insert():
#     database = client["test"]
#     ratings_collection = database["ratings"]
#     ratings_collection.drop()
#     _load_ratings("ratings.csv")
#     ratings_collection.insert_many(ratings)

@app.get("/recomm/{user_id}")
def recommend(user_id : str):
    print("req user_id : ")
    print(int(user_id))
    return getRecomm(client, (int(user_id)))

@app.get("/mongo/info")
async def get_info():
    return {
        "mongo_host" : settings.MONGO_HOST
    }


@app.get("/mongo/menu")
def get_mongo_menu():
   test_db = client["test"]
   menu_cols = test_db["menu"]
   result = menu_cols.find()
   for res in result:
       print(res)
