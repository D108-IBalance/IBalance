from dbUtil.mongodb_api import mongodb_connect, find_all_objec_id
from dbUtil.mysql_api import mysql_connect, update_random_menu_id
from pydantic_settings import BaseSettings
import random

NAME_LIST = ["menu"]


class Settings(BaseSettings):
    MONGO_HOST: str  # 몽고DB 호스트 주소
    MYSQL_HOST: str  # MySQL 호스트 주소
    MYSQL_USER: str  # MySQL 접속 유저 명
    MYSQL_PASSWORD: str  # MySQL 패스워드
    MYSQL_DATABASE: str  # MySQL 접속 대상 스키마


settings = Settings()  # 클래스 객체 생성

uri = f'{settings.MONGO_HOST}'

mongodb_connect(uri)  # 몽고DB 연결
mysql_connect(settings.MYSQL_HOST, settings.MYSQL_USER, settings.MYSQL_PASSWORD, settings.MYSQL_DATABASE)  # MySQL 연결


def change_menu_id():
    _id_list = list()
    _id_list = find_all_objec_id("menu")
    for i in range(1, 48001):
        _id = random.choice(_id_list)
        update_random_menu_id("diet-menu", i, _id)
    pass


if __name__ == '__main__':
    change_menu_id()
