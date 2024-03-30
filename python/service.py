from recommend import init_recommendations, one_recommend, menu_recommend
from request.request_dto import ChildInfo, DietOfMenuId
from dbUtil.mongodb_api import find_by_object_id, find_data_by_attr_condition
from static.mongo_statics import MENU_COLLECTION_NAME, ALLERGY_COLLECTION_NAME
from pre.data_preprocess import menu_info_converter
from custom_exception.exception.custom_http_exception import NotFoundException

"""
@Author: 김회창
"""


def diet_init(request: ChildInfo) -> list[list[dict]]:
    return init_recommendations(request)


def menu_info(menu_id: str) -> dict:
    mongo_result = find_by_object_id(MENU_COLLECTION_NAME, menu_id)
    if len(mongo_result) == 0:
        raise NotFoundException(f'menu_id에 해당하는 데이터가 없습니다, req: menu_id={menu_id}')
    return mongo_result[0]


def one_diet_recommend(request: ChildInfo) -> list[dict]:
    return one_recommend(request)


def new_menu_recommend(request: ChildInfo) -> dict:
    return menu_recommend(request)


def get_menu_names_by_ids(request: list[DietOfMenuId]) -> list[dict]:
    response = list()
    idx = 0
    offset = 4
    menu_id_list = list()
    for diet in request:
        menu_id_list += diet.menuIdList
    mongo_result = find_data_by_attr_condition(menu_id_list, "_id", is_or=True, collection_name=MENU_COLLECTION_NAME,
                                               need_attr=["MEAL_NM"],
                                               exclude_attr=None)
    if len(mongo_result) == 0:
        raise NotFoundException(f'menu_id에 해당하는 데이터가 없습니다, req: menu_ids={menu_id_list}')
    result_table = dict()
    for mongo_data in mongo_result:
        result_table[mongo_data["menu_id"]] = mongo_data["MEAL_NM"]
    for diet in request:
        new_obj = dict()
        new_obj["dietId"] = diet.dietId
        new_obj["menuNameList"] = list()
        for i in range(idx, idx + offset):
            if menu_id_list[i] not in result_table:
                raise NotFoundException(f'menu_id={menu_id_list[i]}가 없습니다.')
            new_obj["menuNameList"].append(result_table[menu_id_list[i]])
        idx += offset
        response.append(new_obj)
    return response


def menu_infos(request: list[str]) -> list[dict]:
    response = list()
    mongo_result = find_data_by_attr_condition(request, "_id", is_or=True, collection_name="menu",
                                               need_attr=None,
                                               exclude_attr=None)
    request_cp = request.copy()
    for result in mongo_result:
        if result["menu_id"] in request_cp:
            request_cp.remove(result["menu_id"])
        response.append(menu_info_converter(result))
    if not len(request_cp) == 0:
        raise NotFoundException(f'request 속 menu_id 리스트 원소 중 일부가 존재하지 않습니다, {request_cp} not in')
    return response


