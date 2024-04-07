from request.request_dto import ChildInfo, Need, DietOfMenuId, PickyWithAllergy
from bson.objectid import ObjectId, InvalidId
from custom_exception.exception.custom_http_exception import BodyValidationException

"""
@Author: 김회창
"""

NEED_TYPE_LIST = ["RICE", "SOUP", "SIDE"]  # needType에 올 수 있는 enum 값 들

"""
string 타입의 validation check 하는 함수
:param: some_str str, 검사할 string
:param: value_name str, 검사할 string의 변수명
:param: list_name str | None, 리스트 속에 포함되었을 경우 리스트 명 
:return: 잘못된 이유가 담긴 문자열 리턴
"""


def _str_checker(some_str: str, value_name: str, list_name: str = None) -> str:
    if some_str is None or not type(some_str) == str or some_str == "":
        msg = f'잘못된 {value_name} 값, req: {value_name}={some_str} '
        if list_name is not None:
            msg += f'in {list_name}'
        return msg
    else:
        return ""


"""
mongodb 의 object_id로서 쓰일 변수에 대한 validation check 하는 함수
:param: _id str, 검사할 id
:param: id_name str, 검사할 string의 변수명
:param: list_name str | None, 리스트 속에 포함되었을 경우 리스트 명 
:param: msg_dict dict | None, 많은 exception 발생 이유를 누적하기 위한 딕셔너리
:return: dict, 잘못된 이유가 누적된 dict 리턴
"""


def object_id_validation_checker(_id: str, id_name: str, list_name: str = None, msg_dict: dict = None) -> dict:
    try:
        ObjectId(_id)
        if msg_dict is not None:
            result = _str_checker(_id, id_name, list_name)
            if not result == "":
                msg_dict["msgList"].append(result)
    except InvalidId:
        msg = f'{id_name} 형식 오류, 잘못된 {id_name}입니다. '
        msg += f'{id_name}: {_id} '
        if list_name is not None:
            msg += f'in {list_name}'
        msg_dict["msgList"].append(msg)
        raise BodyValidationException(msg_dict["msgList"])
    return msg_dict


"""
menu 컬렉션에서 사용될 menu_id에 대한 validation check 하는 함수
:param: menu_id_list list[str], 검사할 menu_id 리스트
:param: is_can_duplicated, 검사할 menu_id의 중복을 허용 할 것인지 아닌지 여부
:return: dict, 잘못된 이유가 누적된 dict 리턴
"""


def menu_id_list_validation_checker(menu_id_list: list[str], is_can_duplicated: bool = None):
    response = dict()
    response["msgList"] = list()
    menu_id_set = set(menu_id_list)
    if is_can_duplicated and not len(menu_id_set) == len(menu_id_list):
        response["msgList"].append(f'menu_id 리스트속 중복된 menu_id가 존재합니다.')
    for menu_id in menu_id_list:
        response = object_id_validation_checker(menu_id, "menu_id", msg_dict=response)
    if len(response["msgList"]) > 0:
        raise BodyValidationException(response["msgList"])
    return response


"""
allergy 컬렉션의 allergy_name에 대한 string validation check 하는 함수
:param: allergy_name str, 검사 할 allergy_name 문자열
:return: str, 잘못된 이유가 담긴 문자열 리턴
"""


def _allergy_name_validation_checker(allergy_name: str) -> str:
    return _str_checker(allergy_name, "allergy_name", "allergyList")


"""
menu 컬렉션의 cache_list에 대한 validation check 하는 함수
:param: cache_list list[str], 검사 할 allergy_name 문자열
:param: response dict, 많은 exception 발생 이유를 누적하기 위한 딕셔너리
:return: dict, 잘못된 이유가 누적된 dict 리턴
"""


def _cache_list_validation_checker(cache_list: list[str], response: dict) -> dict:
    if not type(cache_list) == list:
        response["msgList"] = f'잘못된 cacheList 형식, cacheList 타입이 잘못되었습니다. req: type={type(cache_list)}'
        return response
    if not len(cache_list) == 0:
        for menu_id in cache_list:
            response = object_id_validation_checker(menu_id, "menu_id", "cacheList", response)
    return response


"""
childId에 대한 validation check 하는 함수
:param: child_id int, 검사 할 아이의 고유 번호 문자열
:return: str, 잘못된 이유가 담긴 문자열 리턴
"""


def _child_id_validation_checker(child_id: int) -> str:
    if child_id is None or not type(child_id) == int or child_id <= 0:
        return f'잘못된 childId 값, req: child_id={child_id}'
    else:
        return ""


"""
allergy_list 대한 validation check 하는 함수
:param: allergy_list list[str], 검사 할 알러지 이름이 담긴 문자열 리스트
:return: str, 잘못된 이유가 담긴 문자열 리턴
"""


def _allergy_list_validation_checker(allergy_list: list[str]) -> str:
    if not type(allergy_list) == list:
        return f'잘못된 allergyList 형식, allergyList의 타입이 잘못되었습니다. req: type={type(allergy_list)}'
    if not len(allergy_list) == 0:
        for allergy_name in allergy_list:
            result = _allergy_name_validation_checker(allergy_name)
            if not result == "":
                return result
    return ""


"""
need 대한 validation check 하는 함수
:param: need Need, 검사 할 need 객체
:return: str, 잘못된 이유가 담긴 문자열 리턴
"""


def _need_validation_checker(need: Need) -> str:
    if not type(need) == Need:
        return f'잘못된 need 형식, need의 타입이 잘못되었습니다. req: need={type(need)}'
    if need.protein <= 0 or need.calories <= 0 or need.cellulose <= 0 or need.carbohydrate <= 0:
        return f'잘못된 need 형식, need객체 속 값 중 0 이하가 있습니다.'
    return ""


"""
need_type 대한 validation check 하는 함수
:param: need_type str, 검사 할 need_type 문자열
:return: str, 잘못된 이유가 담긴 문자열 리턴
"""


def _need_type_validation_checker(need_type: str) -> str:
    global NEED_TYPE_LIST
    result = _str_checker(need_type, "needType")
    if not result == "":
        return result
    if need_type not in NEED_TYPE_LIST:
        return f'잘못된 needType 형식, needType 값이 잘못되었습니다. req: {need_type}'
    return ""


"""
current_menu_id_of_diet 대한 validation check 하는 함수
:param: current_menu_id_of_diet list[str], 검사 할 menu_id 문자열 리스트
:return: dict, 잘못된 이유가 누적된 dict 리턴
"""


def _current_menu_id_of_diet_validation_check(current_menu_id_of_diet: list[str], response: dict) -> dict:
    if not type(current_menu_id_of_diet) == list:
        response["msgList"].append(
            f'잘못된 currentMeunIdOfDiet 형식, currentMeunIdOfDiet 타입이 잘못되었습니다. req: currentMeunIdOfDiet={type(current_menu_id_of_diet)}')
        return response
    if not len(current_menu_id_of_diet) == 0:
        for menu_id in current_menu_id_of_diet:
            response = object_id_validation_checker(menu_id, "menu_id", "currentMeunIdOfDiet", response)

    return response


"""
ChildInfo객체속 각각의 원소들을 각 알맞은 validation checker를 통해 올바른 원인을 담은 체로 http_custom_exception을 일으킬 수 있는 함수
:param: request ChildInfo, 검사 할 ChildInfo 객체
"""


def child_info_request_validation_checker(request: ChildInfo):
    response = dict()
    response["msgList"] = list()
    result = _child_id_validation_checker(request.childId)
    if not result == "":
        response["msgList"].append(result)
    if request.allergyList is not None:
        result = _allergy_list_validation_checker(request.allergyList)
        if not result == "":
            response["msgList"].append(result)
    if request.cacheList is not None:
        result = _cache_list_validation_checker(request.cacheList, response)
        response = result
    if request.need is not None:
        result = _need_validation_checker(request.need)
        if not result == "":
            response["msgList"].append(result)
    if request.needType is not None:
        result = _need_type_validation_checker(request.needType)
        if not result == "":
            response["msgList"].append(result)
    if request.currentMenuIdOfDiet is not None:
        result = _current_menu_id_of_diet_validation_check(request.currentMenuIdOfDiet, response)
        response = result
    if len(response["msgList"]) > 0:
        raise BodyValidationException(response["msgList"])


"""
식단에 따른 메뉴 고유 id에 대하여, 고유 id 리스트의 형식 및 사이즈등의 validation check 하는 함수
:param: request list[DietOfMenuId], request body로 넘어와 validation check를 할 대상
"""


def diet_of_menu_id_validation_checker(request: list[DietOfMenuId]):
    response = dict()
    response["msgList"] = list()
    if not type(request) == list:
        response["msgList"].append(f'잘못된 형식, req: {type(request)}')
    if len(request) == 0:
        response["msgList"].append(f'잘못된 형식, 사이즈가 0입니다.')
    if not len(request) == 0:
        for diet_of_menu_id in request:
            if diet_of_menu_id.dietId == "":
                response["msgList"].append(f'잘못된 형식, req: diet_id가 비었습니다.')
                break
            if len(diet_of_menu_id.menuIdList) < 4:
                response["msgList"].append(
                    f'잘못된 형식, req: dietId={diet_of_menu_id.dietId}, menuIdList의 크기가 4보다 작습니다.')
                break
            if not len(diet_of_menu_id.menuIdList) == 0:
                for menu_id in diet_of_menu_id.menuIdList:
                    result = object_id_validation_checker(menu_id, "menu_id", "menuIdList", response)
                    response = result

    if len(response["msgList"]) > 0:
        raise BodyValidationException(response["msgList"])


"""
편식 해결 레시피 디테일 페이지용 validation check하는 함수
:param: recipe_id str, 해당 편식 식재료 내 object_id
:param: matrl_name str, 해당 편식 식재료 명
"""


def picky_detail_validation_checker(matrl_name: str, recipe_id: str):
    response = dict()
    response["msgList"] = list()
    response = object_id_validation_checker(recipe_id, "recipe_id", msg_dict=response)
    result = _str_checker(matrl_name, "matrl_name")
    if not result == "":
        response["msgList"].append(result)

    if len(response["msgList"]) > 0:
        raise BodyValidationException(response["msgList"])


"""
PickyWithAllergy 객체 validation check하는 함수
:param: request PickyWithAllergy, 아이의 편식 식재료명 리스트와 알러지 이름 리스트가 포함된 객체
"""


def picky_with_allergy_validation_check(request: PickyWithAllergy):
    response = dict()
    response["msgList"] = list()
    if not type(request) == PickyWithAllergy:
        response["msgList"].append(f'잘못된 형식, request body 속 데이터가 형식이 맞지 않습니다.')
        raise BodyValidationException(response["msgList"])
    if not len(request.allergyNameList) == 0:
        for allergy_name in request.allergyNameList:
            result = _allergy_name_validation_checker(allergy_name)
            if not result == "":
                response["msgList"].append(result)
                break
    if len(request.pickyMatrlList) == 0:
        response["msgList"].append(f'잘못된 형식, 편식 식재료 리스트가 비었습니다.')
    else:
        for picky_matrl in request.pickyMatrlList:
            result = _str_checker(picky_matrl, "picky_matrl_name", "pickyMatrlList")
            if not result == "":
                response["msgList"].append(result)
                break
    if not len(response["msgList"]) == 0:
        raise BodyValidationException(response["msgList"])
