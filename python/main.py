from fastapi import FastAPI
from recommend import getRecomm
from pydantic_settings import BaseSettings
from dbUtil.mongodb_api import mongodb_connect
from dbUtil.mysql_api import mysql_connect
from request.request_dto import ChildInfo

"""
@Author : 김회창
"""

class Settings(BaseSettings):
    """
    pydantic 라이브러리를 이용하여 환경변수를 실행옵션으로 주입받는 클래스
    """
    MONGO_HOST: str # 몽고DB 호스트 주소
    MYSQL_HOST: str # MySQL 호스트 주소
    MYSQL_USER: str # MySQL 접속 유저 명
    MYSQL_PASSWORD: str # MySQL 패스워드
    MYSQL_DATABASE: str # MySQL 접속 대상 스키마




settings = Settings() # 클래스 객체 생성

app = FastAPI() # Fast API 앱 객체 생성

uri = f'{settings.MONGO_HOST}'

mongodb_connect(uri) # 몽고DB 연결
mysql_connect(settings.MYSQL_HOST, settings.MYSQL_USER, settings.MYSQL_PASSWORD, settings.MYSQL_DATABASE) # MySQL 연결


@app.get("/recomm/init")
def init_recommend(request: ChildInfo):
    """
    :param: ChildInfo
    :ChildInfo: childId: 아이 고유번호, allergyList: 그 아이가 갖고있는 알러지, cacheList: 그날 새로고침 되었던 menu_id
    :return: List of Dictionary
    :Diet:
    """
    return getRecomm((int(request.childId)),0)

