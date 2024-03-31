from recommend import init_recommendations, one_recommend, menu_recommend
from request.request_dto import ChildInfo, DietOfMenuId, PickyWithAllergy
from dbUtil.mongodb_api import find_by_object_id, find_data_by_attr_condition, find_picky_recipes
from static.mongo_statics import MENU_COLLECTION_NAME, ALLERGY_COLLECTION_NAME
from pre.data_preprocess import menu_info_converter
from custom_exception.exception.custom_http_exception import NotFoundException

"""
@Author: 김회창
"""

"""
아이의 정보를 받아서 요일별 초기식단 7개를 리턴하는 함수
:param: request ChildInfo, 아이의 알러지 정보, 아이의 고유번호 등의 아이에 관한 정보가 담긴 객체
:return: list[list[dict]] ubcf 알고리즘을 거쳐 완성된 초기식단 7개를 리턴
"""


def diet_init(request: ChildInfo) -> list[list[dict]]:
    return init_recommendations(request)


"""
메뉴 id 값을 받아서 메뉴 컬렉션에서 해당하는 메뉴 데이터를 리턴하는 함수
:param: menu_id str, 고유 메뉴id값
:return: dict, menu_id에 따른 메뉴 데이터를 mongodb로 부터 받아 리턴
"""


def menu_info(menu_id: str) -> dict:
    mongo_result = find_by_object_id(MENU_COLLECTION_NAME, menu_id)
    if len(mongo_result) == 0:
        raise NotFoundException(f'menu_id에 해당하는 데이터가 없습니다, req: menu_id={menu_id}')
    return menu_info_converter(mongo_result[0])


"""
하나의 식단을 새롭게 추천 받는 함수
:param: request ChildInfo, 아이의 알러지 정보, 아이의 고유번호 등의 아이에 관한 정보가 담긴 객체
:return: list[dict] ubcf 알고리즘을 거쳐 새로운 하나의 식단 리턴
"""


def one_diet_recommend(request: ChildInfo) -> list[dict]:
    return one_recommend(request)


"""
하나의 메뉴를 새롭게 추천 받는 함수
:param: request ChildInfo, 아이의 알러지 정보, 아이의 고유번호 등의 아이에 관한 정보가 담긴 객체
:return: list[dict] ubcf 알고리즘을 거쳐 필요한 메뉴의 타입에 따라 새로운 하나의 메뉴 리턴
"""


def new_menu_recommend(request: ChildInfo) -> dict:
    return menu_recommend(request)


"""
식단 고유번호와 그 식단에 포함되어 있는 메뉴 아이디 리스트를 가지고 식단 고유번호에 따른 메뉴 정보를 리턴하는 함수
:param: request list[DietOfMenuId], 식단 별 고유 식단 id와 포함된 메뉴의 고유 id 리스트를 가진 객체 리스트
:return: list[dict], 각 식단별 고유 메뉴 id 리스트에 따른 메뉴정보를 리턴 
"""


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


"""
메뉴 id 리스트를 받아서 각 id에 따른 메뉴 정보 리스트를 리턴하는 함수
:param: request list[str], 메뉴에 고유 id 리스트
:return: 각 고유id에 따른 메뉴 정보 리스트를 리턴 
"""


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


"""
편식하는 식재료 및 알러지 정보를 갖고서 페이지네이션 기능이 부여된 레시피 정보 제공
:param: last_id str | None, 이전에 받았던 레시피 리스트 속 마지막 recipe_id 값, None의 경우 가장 첫 document를 기준으로 offset만큼 제공
:param: offset int, 한번에 보여줄 레시피 리스트 개수
:param: allergy_name_list list[str], 알러지 이름 리스트
:param: picky str, 편식하는 식재료 명
:return: list[dict], 알러지 위혐 요인은 뺀 체로, 편식하는 식재료 정보가 포함된 레시피 정보 리스트 리턴
"""


def picky_recipes(last_id: str | None, offset: int, allergy_name_list: list[str], picky: str) -> list[dict]:
    exclude_attr = ["_id"]
    hazard = None
    if not len(allergy_name_list) == 0:
        hazard = []
        allergy_obj_list = find_data_by_attr_condition(allergy_name_list, "allergy_name", is_or=True,
                                                       collection_name="allergy", need_attr=None,
                                                       exclude_attr=exclude_attr)
        for allergy_obj in allergy_obj_list:
            for hazard_menu in allergy_obj["allergy_hazard"]:
                hazard.append(hazard_menu)
    result = find_picky_recipes(picky, offset, last_id=last_id, exclude_materials=hazard)
    if len(result) == 0:
        raise NotFoundException(
            f'해당하는 데이터가 없습니다, query : lastid={last_id}, offset={offset}, matrl={picky}, req: {allergy_name_list}')
    return result


"""
편식 식재료 명과 해당 고유 레시피 id를 갖고서 id에 대응되는 레시피 정보를 제공하는 함수
:param: matrl_name str, 편식 식재료 명
:param: recipe_id str, 레시피 id
:return: dict, 해당 레시피의 정보를 리턴
"""


def picky_recipe(matrl_name: str, recipe_id: str) -> dict:
    result = find_by_object_id(matrl_name, recipe_id, "recipe")
    if len(result) == 0:
        raise NotFoundException(f'{recipe_id} 에 해당하는 레시피가 없습니다. path: recipe_id={recipe_id}, matrl_name={matrl_name}')
    return result[0]


"""
편식 식재료 명 리스트와 알러지 정보 리스트를 가진 객체를 가지고 식재료 명에 따른 5개의 레시피 정보를 제공하는 함수
:param: request PickyWithAllergy, 편식 식재료 명 리스트와 알러지 정보 리스트가 포함된 객체
:return: dict, 각 편식 식재료에 대응되는 5개의 레시피 정보가 포함된 dict를 리턴
"""


def picky_main(request: PickyWithAllergy) -> dict:
    hazard = None
    offset = 5
    if not len(request.allergyNameList) == 0:
        hazard = list()
        hazard_exclude_attr = ["_id"]
        allergy_obj_list = find_data_by_attr_condition(request.allergyNameList, "allergy_name", is_or=True,
                                                       collection_name="allergy", need_attr=None,
                                                       exclude_attr=hazard_exclude_attr)
        for allergy_obj in allergy_obj_list:
            for hazard_menu in allergy_obj["allergy_hazard"]:
                hazard.append(hazard_menu)
    result = dict()
    exclude_attr = ["recipe_material_list", "recipe_steps"]
    for picky_name in request.pickyMatrlList:
        recipes = find_picky_recipes(picky_name, offset, last_id=None, exclude_materials=hazard, exclude_attr=exclude_attr)
        result[picky_name] = recipes
    return result
