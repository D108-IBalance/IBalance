from pymongo.mongo_client import MongoClient
from pymongo.server_api import ServerApi
from bson.objectid import ObjectId
from pre.data_preprocess import object_id_converter
from static.mongo_statics import DATABASE_NAME

"""
@Author: 김회창
"""

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
:param: query dict, mongodb에 사용할 query
:param: project dict, mongodb에서 특별하게 뽑을 컬럼이 있을시 사용하는 query
:param: id_alias, mongodb에서 꺼내온 데이터 중 _id가 있는 경우 변환시켜줄 때 사용할 새로운 id명
:param: is_multipl bool, 복수개의 데이터인지 아닌지에 따라 반복문 시도 여부 결정
:param: limit int | None, 만약 페이지네이션이 필요한 데이터의 경우 limit절 사용
"""


def _execute(collection_name, query: dict, project: dict, id_alias: str, is_multiple: bool, limit: int = None) -> list[dict]:
    result = list()
    global client
    if not validation_check(collection_name):
        return result
    print(f'collection_name: {collection_name}, query: {query}')
    collection = client[DATABASE_NAME][collection_name]
    if project is None:
        if limit is not None:
            result = list(collection.find(query).limit(limit))
        else:
            result = list(collection.find(query))
    else:
        if limit is not None:
            result = list(collection.find(query, project).limit(limit))
        else:
            result = list(collection.find(query, project))
    ret = []
    if result:
        for res in result:
            ret.append(object_id_converter(res, id_alias))
    return ret


"""
mongodb의 고유 id값을 사용해서 해당하는 데이터를 반환하는 함수
:param: collection_name str, mongodb내 쿼리를 실행시킬 collection 이름
:param: _id str, objectId 로 변환 시킬 고유 값
:return: dictionary, 쿼리를 실행시킨 결과 리턴
"""


def find_by_object_id(collection_name: str, _id: str, id_alias: str = "menu") -> list[dict]:
    query = {"_id": ObjectId(_id)}
    project = None
    result = _execute(collection_name, query, project,id_alias=id_alias, is_multiple=False)
    return result


"""
mongodb 내 모든 데이터 조회하는 함수
:param: collection_name str, mongodb에서 쿼리를 실행시킬 collection 이름
:return: dictionary, 쿼리를 실행시킨 결과 리턴
"""


def find_all_data(collection_name, id_alias: str = "menu") -> list[dict]:
    query = {}
    project = None
    result = _execute(collection_name, query, project,id_alias=id_alias, is_multiple=True)
    return result


"""
mongodb 내 모든 데이터에 대하여 특정 속성만 조회하는 함수
:param: collection_name str, mongodb에서 쿼리를 실행시킬 collection 이름
:param: attr_name str, 데이터들 내 공통 속성 명
:return: list[dict], 해당 컬렉션의 모든 데이터에 대하여 attr_name에 해당하는 어트리뷰트들을 리턴
"""


def find_all_attr(collection_name: str, attr_name: str, id_alias: str = "menu") -> list[dict]:
    result = list()
    query = {}
    project = {
        attr_name: 1
    }
    return _execute(collection_name, query, project,id_alias=id_alias, is_multiple=True)


"""
mongodb 내 object_id가 id인 데이터에 대하여 특정 속성만 조회하는 함수
:param: collection_name str, mongodb에서 쿼리를 실행시킬 collection 이름
:param: attr_name str, 데이터들 내 공통 속성 명
:param: id str, mongodb내 object_id로서 변환되어 사용될 값
:return: list[dict], object_id인 데이터에 대하여 attr_name 속성값을 리턴
"""


def find_attr_by_id(collection_name: str, attr_name: str, id: str, id_alias: str = "menu") -> list[dict]:
    query = {
        "_id": ObjectId(id)
    }
    project = {
        attr_name: 1
    }
    result = _execute(collection_name, query, project, id_alias=id_alias, is_multiple=False)
    return result


"""
mongodb 내에서 하나의 attribute에 대하여 여러 조건들을 각 논리연산자에 맞게 조회하기 위한 함수
:param: condition_list list, 특정 attribute 명에 대하여 is_or값의 맞게 더해지는 조건 리스트
:param: attr_name str, 조건에 대상이 되는 attribute 명
:param: is_or bool, or 인지 and인지 구분짓는 값
:param: collection_name str, 사용하는 컬렉션 명 
:param: need_attr list[str] | None, 검색 조건에 이어 필요한 attribute가 있는 경우
:param: exclude_attr list[str] | None, 검색 조건에 이어 필요없는 attribute가 있는 경우
:return: list[dict], 알러지 이름 및 위험한 식품 정보가 담겨있는 객체 리스트를 리턴
"""


def find_data_by_attr_condition(condition_list: list, attr_name: str, is_or: bool, collection_name: str,
                                id_alias: str = "menu",
                                need_attr: list[str] = None,
                                exclude_attr:list[str] = None) -> list[dict]:
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

    is_multiple = True
    if len(query[operator]) == 1:
        is_multiple = False

    return _execute(collection_name, query, project, id_alias=id_alias, is_multiple=is_multiple)


"""
mongodb 내 편식 식재료 컬렉션에서 마지막 id값 보다 크면서 offset만큼의 document를 조회하는 함수
:param: collection_name str, 사용하는 컬렉션 명
:param: offset int, 보여질 item 개수
:param: last_id str, 이전에 보여졌던 item리스트 중 마지막 id값
:param: exclude_materials list[str] | None, 제외시켜야 할 식재료 명
:return: list[dict], 제외시켜야 할 식재료 조건이 적용된 해당하는 편식 식재료에 대한 레시피 리스트를 리턴
"""


def find_picky_recipes(collection_name: str, offset: int, last_id: str | None, exclude_materials: list[str] = None) -> list[dict]:
    query = {}
    if last_id is not None and not last_id == "":
        query = {
            "$and": [
                {"_id": {"$gt": ObjectId(last_id)}}
            ]
        }
    if exclude_materials is not None:
        sub_query = {
            "recipe_material_list.material_name": {
                "$not": {
                    "$regex": ""
                }
            }
        }
        regex_str = "|".join(exclude_materials)
        sub_query["recipe_material_list.material_name"]["$not"]["$regex"] = regex_str
        query["$and"].append(sub_query)
    project = {}
    return _execute(collection_name, query, project, id_alias="recipe", is_multiple=True, limit=offset)
