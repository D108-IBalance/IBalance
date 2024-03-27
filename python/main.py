from fastapi import FastAPI

from pre import pre_call_data
from pre.data_preprocess import menu_info_converter
from recommend import init_recommendations, one_recommend, menu_recommend
from pydantic_settings import BaseSettings
from dbUtil.mongodb_api import mongodb_connect, find_attr_by_id, find_all_data, find_by_object_id
from dbUtil.mysql_api import mysql_connect, find_all_rating
from request.request_dto import ChildInfo
import warnings
import importlib

# 특정 유형의 경고를 필터링
warnings.filterwarnings("ignore", category=RuntimeWarning)

"""
@Author : 김회창
"""

"""
pydantic 라이브러리를 이용하여 환경변수를 실행옵션으로 주입받는 클래스
"""


class Settings(BaseSettings):
    MONGO_HOST: str  # 몽고DB 호스트 주소
    MYSQL_HOST: str  # MySQL 호스트 주소
    MYSQL_USER: str  # MySQL 접속 유저 명
    MYSQL_PASSWORD: str  # MySQL 패스워드
    MYSQL_DATABASE: str  # MySQL 접속 대상 스키마
    MYSQL_PORT: str  # MYSQL 접속 포트


settings = Settings()  # 클래스 객체 생성

app = FastAPI()  # Fast API 앱 객체 생성

uri = f'{settings.MONGO_HOST}'

mongodb_connect(uri)  # 몽고DB 연결
mysql_connect(settings.MYSQL_HOST, settings.MYSQL_USER, settings.MYSQL_PASSWORD, settings.MYSQL_DATABASE,
              settings.MYSQL_PORT)  # MySQL 연결

pre_call_data.pre_call()

from pre.pre_call_data import TABLE

"""
초창기 식단 7개를 한번에 추천해주는 컨트롤러
"""


@app.post("/recomm/init")
def init_recommend(request: ChildInfo):
    return init_recommendations(request)


"""
메뉴 고유id를 통해 메뉴 정보 조회하는 컨트롤러
"""


@app.get("/recomm/info/{menu_id}")
def get_menu_info(menu_id: str):
    data = find_by_object_id("menu", menu_id)
    return menu_info_converter(data[0])


"""
단일 식단 조회하는 컨트롤러
"""


@app.post("/recomm")
def refresh_recommend(request: ChildInfo):
    return one_recommend(request)


"""
식단 내 메뉴 교체하는 컨트롤러
"""


@app.post("/recomm/menu")
def refresh_menu(request: ChildInfo):
    return menu_recommend(request)
