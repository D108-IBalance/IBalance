from bson.objectid import ObjectId

"""
@Author: 김회창
"""

"""
menu 데이터의 mongodb에서 부여받은 고유 id값을 menu_id로 변환
:param: menu_obj dictionary, 메뉴 하나의 데이터
:return: menu_obj dictionary
"""


def menu_pre(menu_obj):
    if "_id" not in menu_obj.keys():
        return menu_obj
    menu_obj["menu_id"] = str(ObjectId(menu_obj["_id"]))
    del menu_obj["_id"]
    return menu_obj


"""
하나의 식단내 4가지 메뉴를 반복문을 통해 api 명세에 맞춘 key : value 쌍으로 정제
:return: result list[dict], 하나의 식단에 밥류 1개, 반찬류 2개, 국 1개의 총 4개 메뉴 정보를 정제한 리스트 리턴
"""


def diet_converter(diet):
    new_diet = list()
    for menu in diet:
        new_menu_obj = dict()
        new_menu_obj["recipe"] = dict()
        new_menu_obj["recipe"]["content_list"] = menu["COOK_MTH_CONT"].split("<br>")
        new_menu_obj["nutrient"] = dict()
        new_menu_obj["nutrient"]["protein"] = menu["PROTEIN_QY"]
        new_menu_obj["nutrient"]["fat"] = menu["FAT_QY"]
        new_menu_obj["nutrient"]["carbohydrate"] = menu["CARBOH_QY"]
        new_menu_obj["material"] = menu["MATRL_NM"].split(", ")
        if menu["MEAL_CLSF_NM"] in ["밥류", "죽류"]:
            new_menu_obj["type"] = "밥"
        elif menu["MEAL_CLSF_NM"] in ["국,탕류"]:
            new_menu_obj["type"] = "국"
        else:
            new_menu_obj["type"] = "반찬"
        new_menu_obj["img_url"] = menu["MEAL_PICTR_FILE_NM"]
        new_menu_obj["menu_id"] = menu["menu_id"]
        new_menu_obj["menu_name"] = menu["MEAL_NM"]
        new_menu_obj["cal"] = menu["CALORIE_QY"]
        new_diet.append(new_menu_obj)
    return new_diet