from fastapi import FastAPI

from pre import pre_call_data
from pre.data_preprocess import menu_info_converter, parse_matrl_name
from recommend import init_recommendations, one_recommend, menu_recommend
from pydantic_settings import BaseSettings
from dbUtil.mongodb_api import mongodb_connect, find_attr_by_id, find_all_data, find_by_object_id, \
    find_data_by_attr_condition, find_all_attr
from dbUtil.mysql_api import mysql_connect, find_all_rating
from request.request_dto import ChildInfo, DietOfMenuId
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


"""
요일 내 식단들의 고유 메뉴id리스트를 받아서 각 식단별 메뉴 이름 리스트를 조회하는 컨트롤러
"""


@app.post("/recomm/info/diet")
def get_menu_names(request: list[DietOfMenuId]):
    response = list()
    idx = 0
    menu_id_list = list()
    for diet in request:
        menu_id_list += diet.menuIdList
    mongo_result = find_data_by_attr_condition(menu_id_list, "_id", is_or=True, collection_name="menu",
                                               need_attr=["MEAL_NM"],
                                               exclude_attr=None)
    result_table = dict()
    for mongo_data in mongo_result:
        result_table[mongo_data["menu_id"]] = mongo_data["MEAL_NM"]
    for diet in request:
        new_obj = dict()
        new_obj["dietId"] = diet.dietId
        new_obj["menuNameList"] = list()
        for i in range(idx, idx + 4):
            new_obj["menuNameList"].append(result_table[menu_id_list[i]])
        idx += 4
        response.append(new_obj)
    return response


"""
식단 내 4개의 메뉴 id 리스트를 받아서 해당 메뉴의 이름, 식재료 리스트, 이미지를 조회
"""

@app.post("/recomm/info")
def get_menu_infos(request: list[str]):
    response = list()
    mongo_result = find_data_by_attr_condition(request, "_id", is_or=True, collection_name="menu",
                                               need_attr=["MEAL_NM", "MATRL_NM", "MEAL_PICTR_FILE_NM"],
                                               exclude_attr=None)
    for result in mongo_result:
        new_obj = dict()
        new_obj["menuId"] = result["menu_id"]
        new_obj["menuName"] = result["MEAL_NM"]
        new_obj["menuMaterial"] = parse_matrl_name(result["MATRL_NM"])
        new_obj["menuImgUrl"] = result["MEAL_PICTR_FILE_NM"]
        response.append(new_obj)
    return response