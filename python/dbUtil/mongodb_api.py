from pymongo.mongo_client import MongoClient
from pymongo.server_api import ServerApi
from bson.objectid import ObjectId
from pre.data_preprocess import menu_pre

"""
@Author: 김회창
"""

DATABASE_NAME = "ibalance"  #
# 접속 할 데이터베이스 이름
client = None  # pymongo 클라이언트 접속 객체
collection_name_list = []  # 현재 mongodb 내 컬렌션 이름들
last_uri = None  # 마지막 접속 uri를 캐싱해놓는 전역변수

"""
mongodb에 접속하는 함수, 단일 클라이언트 객체를 유지시키기 위해 사용
:param: uri str, 접속에 필요한 uri
"""


def mongodb_connect(uri):
    global client
    global collection_name_list
    global last_uri
    last_uri = uri
    if client is None:
        client = MongoClient(last_uri, server_api=ServerApi('1'))
        collection_name_list = client[DATABASE_NAME].list_collection_names()
    else:
        print("already connected")
    try:
        client.admin.command('ping')
        print("Pinged your deployment. You successfully connected to MongoDB!")
    except Exception as e:
        print(e)


"""
현재 클라이언트가 살아있는지, 데이터베이스 내에 없는 컬렉션에 접근하려는건 아닌지 등을 검사하는 함수
만약 클라이언트 접속정보가 죽었다면, 재접속 시도
:param: collection_name str, 검사할 컬렉션 이름
"""


def validation_check(collection_name: None):
    global client
    global collection_name_list
    global last_uri
    if collection_name is None:
        print("collection_name is empty")
        return False
    if last_uri is None:
        print("cache Error: last_uri is empty")
        return False
    if client is None:
        print("mongodb_client is empty, reconnect mongodb...")
        mongodb_connect(last_uri)
    if collection_name not in collection_name_list:
        print(f'no collection Error: parameter = {collection_name}')
        return False
    return True


"""
private 함수로서 현재 파일내에서만 사용한다.
각각의 목적에 맞는 함수로 부터 query를 받아서 실행시키기 전 validation 체크를 하고, mongodb 고유 id값을 변환시켜서 객체를 리턴
:param: collection_name str, mongodb 의 컬렉션 이름
:param: query dictionary, mongodb에 사용할 query
:param: project dictionary, mongodb에서 특별하게 뽑을 컬럼이 있을시 사용하는 query
:param: is_multipl bool, 복수개의 데이터인지 아닌지에 따라 반복문 시도 여부 결정
"""


def _execute(collection_name, query: dict, project: dict, is_multiple: bool):
    result = dict()
    print(f'query: {query}')
    global client
    if is_multiple:
        result = list()
    if not validation_check(collection_name):
        return result

    collection = client[DATABASE_NAME][collection_name]
    if project is None:
        result = collection.find(query)
    else:
        result = collection.find(query, project)

    ret = []
    print(f'type : {type(result)}, data : {result}')
    if is_multiple:
        for res in result:
            if collection_name == "menu":
                ret.append(menu_pre(res))
            else: ret.append(res)
    else:
        if collection_name == "menu":
            ret.append(menu_pre(result.next()))
        else: ret.append(result.next())

    return ret


"""
mongodb의 고유 id값을 사용해서 해당하는 데이터를 반환하는 함수
:param: collection_name str, mongodb내 쿼리를 실행시킬 collection 이름
:param: _id str, objectId 로 변환 시킬 고유 값
:return: dictionary, 쿼리를 실행시킨 결과 리턴
"""


def find_by_object_id(collection_name, _id: str):
    query = {"_id": ObjectId(_id)}
    project = None
    result = _execute(collection_name, query, project, is_multiple=False)
    return result


"""
mongodb 내 모든 데이터 조회하는 함수
:param: collection_name str, mongodb에서 쿼리를 실행시킬 collection 이름
:return: dictionary, 쿼리를 실행시킨 결과 리턴
"""


def find_all_data(collection_name):
    query = {}
    project = None
    result = _execute(collection_name, query, project, is_multiple=True)
    return result


"""
mongodb 내 모든 데이터에 대하여 특정 속성만 조회하는 함수
:param: collection_name str, mongodb에서 쿼리를 실행시킬 collection 이름
:attr_name: 데이터들 내 공통 속성 명
:return: dictionary, 쿼리를 실행시킨 결과 리턴
"""


def find_all_attr(collection_name, attr_name):
    result = list()
    query = {}
    project = {
        attr_name: 1
    }
    return _execute(collection_name, query, project, is_multiple=True)


"""
mongodb 내 모든 데이터에 대하여 특정 속성만 조회하는 함수
:param: collection_name str, mongodb에서 쿼리를 실행시킬 collection 이름
:attr_name: 데이터들 내 공통 속성 명
:return: dictionary, 쿼리를 실행시킨 결과 리턴
"""


def find_attr_by_id(collection_name, attr_name, id):
    query = {
        "_id": ObjectId(id)
    }
    project = {
        attr_name: 1
    }
    return _execute(collection_name, query, project, is_multiple=False)


"""
mongodb 내 allergy 컬렉션에 대해서 allergy_name 기준으로 데이터를 들고 오는 함수
:param: allergy_name_list list, 알러지 이름 리스트
:return: list[dictionary], 알러지 이름 및 위험한 식품 정보가 담겨있는 객체 리스트
"""


def find_data_by_attr_condition(condition_list: list, attr_name: str, is_or: bool, collection_name: str, need_attr=None, exclude_attr=None):
    operator = "$or"
    if not is_or:
        operator = "$and"
    query = {operator: list()}
    project = {}
    if need_attr:
        for need_attr_name in need_attr:
            project[need_attr_name] = 1
    if exclude_attr:
        for exclude_attr_name in exclude_attr:
            project[exclude_attr_name] = 0

    for condition in condition_list:
        sub_query = dict()
        if attr_name == "_id":
            sub_query[attr_name] = ObjectId(condition)
        else:
            sub_query[attr_name] = condition
        query[operator].append(sub_query)

    is_multiple=True
    if len(query[operator]) == 1:
        is_multiple=False

    return _execute(collection_name, query, project, is_multiple=is_multiple)
