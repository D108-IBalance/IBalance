from request.request_dto import ChildInfo, Need, DietOfMenuId
from bson.objectid import ObjectId, InvalidId
from custom_exception.exception.custom_http_exception import BodyValidationException

"""
@Author: 김회창
"""

NEED_TYPE_LIST = ["RICE", "SOUP", "SIDE"]


def _str_checker(some_str, value_name, list_name=None) -> str:
    if some_str is None or not type(some_str) == str or some_str == "":
        msg = f'잘못된 {value_name} 값, req: {value_name}={some_str} '
        if list_name is not None:
            msg += f'in {list_name}'
        return msg
    else:
        return ""


def menu_id_validation_checker(menu_id, list_name=None, msg_dict=None) -> dict:
    try:
        ObjectId(menu_id)
        if msg_dict is not None:
            result = _str_checker(menu_id, "menu_id", list_name)
            if not result == "":
                msg_dict["msgList"].append(result)
    except InvalidId:
        msg = "menu_id 형식 오류, 잘못된 menu_id입니다."
        if list_name is not None:
            msg += f'menu_id: {menu_id} in {list_name}'
        msg_dict["msgList"].append(msg)
        raise BodyValidationException(msg_dict["msgList"])
    return msg_dict


def menu_id_list_validation_checker(menu_id_list: list[str], is_can_duplicated=None):
    response = dict()
    response["msgList"] = list()
    menu_id_set = set(menu_id_list)
    if is_can_duplicated and not len(menu_id_set) == len(menu_id_list):
        response["msgList"].append(f'menu_id 리스트속 중복된 menu_id가 존재합니다.')
    for menu_id in menu_id_list:
        response = menu_id_validation_checker(menu_id, msg_dict=response)
    if len(response["msgList"]) > 0:
        raise BodyValidationException(response["msgList"])


def _allergy_name_validation_checker(allergy_name) -> str:
    return _str_checker(allergy_name, "allergy_name", "allergyList")


def _cache_list_validation_checker(cache_list, response) -> str:
    if not type(cache_list) == list:
        response["msgList"] = f'잘못된 cacheList 형식, cacheList 타입이 잘못되었습니다. req: type={type(cache_list)}'
        return response
    if not len(cache_list) == 0:
        for menu_id in cache_list:
            response = menu_id_validation_checker(menu_id, "cacheList", response)
    return response


def _child_id_validation_checker(child_id) -> str:
    if child_id is None or not type(child_id) == int or child_id <= 0:
        return f'잘못된 childId 값, req: child_id={child_id}'
    else:
        return ""


def _allergy_list_validation_checker(allergy_list) -> str:
    if not type(allergy_list) == list:
        return f'잘못된 allergyList 형식, allergyList의 타입이 잘못되었습니다. req: type={type(allergy_list)}'
    if not len(allergy_list) == 0:
        for allergy_name in allergy_list:
            result = _allergy_name_validation_checker(allergy_name)
            if not result == "":
                return result
    return ""


def _need_validation_checker(need) -> str:
    if not type(need) == Need:
        return f'잘못된 need 형식, need의 타입이 잘못되었습니다. req: need={type(need)}'
    if need.protein <= 0 or need.calories <= 0 or need.cellulose <= 0 or need.carbohydrate <= 0:
        return f'잘못된 need 형식, need객체 속 값 중 0 이하가 있습니다.'
    return ""


def _need_type_validation_checker(need_type) -> str:
    global NEED_TYPE_LIST
    result = _str_checker(need_type, "needType")
    if not result == "":
        return result
    if need_type not in NEED_TYPE_LIST:
        return f'잘못된 needType 형식, needType 값이 잘못되었습니다. req: {need_type}'
    return ""


def _current_menu_id_of_diet_validation_check(current_menu_id_of_diet, response) -> dict:
    if not type(current_menu_id_of_diet) == list:
        response["msgList"].append(
            f'잘못된 currentMeunIdOfDiet 형식, currentMeunIdOfDiet 타입이 잘못되었습니다. req: currentMeunIdOfDiet={type(current_menu_id_of_diet)}')
        return response
    if not len(current_menu_id_of_diet) == 0:
        for menu_id in current_menu_id_of_diet:
            response = menu_id_validation_checker(menu_id, "currentMeunIdOfDiet", response)

    return response


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


def diet_of_menu_id_validation_checker(request: list[DietOfMenuId]) -> dict:
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
                    result = menu_id_validation_checker(menu_id, "menuIdList", response)
                    response = result

    if len(response["msgList"]) > 0:
        raise BodyValidationException(response["msgList"])



