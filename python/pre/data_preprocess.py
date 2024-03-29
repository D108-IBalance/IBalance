from bson.objectid import ObjectId

"""
@Author: 김회창
"""

"""
해당 메뉴의 주요 식재료를 파싱하는 함수
:param: matrl_name str, mongodb에서 가져오는 파싱 전 식재료 문자열
:return: list[str], 파싱 후 문자열
"""

BLACK_LIST_OF_MATRL = """
                    생것 푸른것 노지 일반형 왜간장 유자차 보통 날것 생것 위너 분말 말린것 부산물 재래종 뿌리 자연산 붉은것 튀김가루 생칼국수 돼지고기가공품 삶은국물 볶은것 말린것 일품 가당 자연산 중력분
                    가루 깨소금 개량종 물엿 토마토케찹 판형 근대 자건품대 튀김 삶은것 액젓 개량종 다리 풀무원 백미 빵가루 호상 구근 동원산업 식염 깨소금 도정곡 얼갈이 인스턴트 찐것 골 우동소스 뇌 중화두반장소스 포
                    국내산 줄기 소파 냉동품 튀김옷입혀튀긴것 마른것 레토르트 잼 날개 훈제품 다리살 중휘핑 자건품소 도우넛 튀김냉동품 튀김옷입혀튀긴것 신고 건조
                    """  # 굳이 주요 식재료로 들어가지 않는 재료들 비포함 시키기 위한 문자열

PARSING_TABLE = {  # 비슷한 이름의 음식을 획일화 하기 위한 파싱 테이블
    "김밥용김": "김",
    "맛김": "김",
    "가공우유": "우유",
    "튀긴두부": "두부",
    "밀가공식품": "밀가루",
    "전란": "계란",
    "떡복이": "떡",
    "건 북어": "북어",
    "등심": "돼지고기",
    "안심": "돼지고기",
    "삼겹살": "돼지고기",
    "쇠고기": "소고기",
    "한우": "소고기",
    "수입우": "소고기",
    "우둔": "소고기",
    "사태": "소고기",
    "가래떡": "떡",
    "가공치즈": "치즈",
    "후레쉬참치": "참치",
    "찰옥수수": "옥수수",
    "단옥수수": "옥수수",
    "검정팥": "팥",
    "생선튀김": "생선",
    "늙은호박": "호박",
    "참굴": "굴",
    "스모크햄": "햄",
    "모짜렐라": "치즈",
    "보리새우": "새우",
    "잔새우": "새우",
    "런천미트": "햄"
}


def parse_matrl_name(matrl_nm: str):
    global BLACK_LIST_OF_MATRL
    global PARSING_TABLE
    matrl_list = set()
    matrl_nm = matrl_nm.strip()
    matrl_nm = matrl_nm.replace(", ", "|")
    matrl_nm = matrl_nm.replace(",", "")
    matrl_nm = matrl_nm.split("|")
    for matrl in matrl_nm:
        real_matrl = (matrl.split(" "))[0]
        if real_matrl in BLACK_LIST_OF_MATRL:
            continue
        elif real_matrl in PARSING_TABLE:
            matrl_list.add(PARSING_TABLE[real_matrl])
            continue
        else:
            matrl_list.add(real_matrl)
    return list(matrl_list)


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
하나의 메뉴에 대하여 레시피정보, 메뉴 이름, 필요한 식재료 등의 정보를 response 양식에 맞게 정제하여 리턴
:param: menu_obj dict, mongodb 에서 꺼내온 raw한 메뉴 객체
:return: new_menu_obj dict, response 양식에 맞게 정제된 메뉴 객체 리턴
"""


def menu_converter(menu_obj):
    new_menu_obj = dict()
    new_menu_obj["recipe"] = dict()
    new_menu_obj["recipe"]["content_list"] = menu_obj["COOK_MTH_CONT"].split("<br>")
    new_menu_obj["recipe"]["need"] = menu_obj["MATRL_NM"]
    new_menu_obj["protein"] = menu_obj["PROTEIN_QY"]
    new_menu_obj["fat"] = menu_obj["FAT_QY"]
    new_menu_obj["carbohydrate"] = menu_obj["CARBOH_QY"]
    new_menu_obj["material"] = parse_matrl_name(menu_obj["MATRL_NM"])
    if menu_obj["MEAL_CLSF_NM"] in ["밥류", "죽류"]:
        new_menu_obj["type"] = "밥"
    elif menu_obj["MEAL_CLSF_NM"] in ["국,탕류"]:
        new_menu_obj["type"] = "국"
    else:
        new_menu_obj["type"] = "반찬"
    new_menu_obj["img_url"] = menu_obj["MEAL_PICTR_FILE_NM"]
    new_menu_obj["menu_id"] = menu_obj["menu_id"]
    new_menu_obj["menu_name"] = menu_obj["MEAL_NM"]
    new_menu_obj["cal"] = str(int(float(menu_obj["CALORIE_QY"])))
    return new_menu_obj


"""
하나의 식단내 4가지 메뉴를 반복문을 통해 api 명세에 맞춘 key : value 쌍으로 정제
:return: result list[dict], 하나의 식단에 밥류 1개, 반찬류 2개, 국 1개의 총 4개 메뉴 정보를 정제한 리스트 리턴
"""


def diet_converter(diet):
    new_diet = list()
    for menu in diet:
        new_diet.append(menu_converter(menu))
    return new_diet


"""
메뉴 정보조회 전용 converter
:param: mongodb에서 object_id를 통해 조회한 단일 menu 객체
:return: 필요한 요소만 담긴 response 객체 반환
"""


def menu_info_converter(menu_obj):
    new_obj = dict()
    new_obj["menuId"] = menu_obj["menu_id"]
    new_obj["menuName"] = menu_obj["MEAL_NM"]
    new_obj["menuImgUrl"] = menu_obj["MEAL_PICTR_FILE_NM"]
    if menu_obj["MEAL_CLSF_NM"] in ["밥류", "죽류"]:
        new_obj["menuType"] = "RICE"
    elif menu_obj["MEAL_CLSF_NM"] in ["국,탕류"]:
        new_obj["menuType"] = "SOUP"
    else:
        new_obj["menuType"] = "SIDE"
    new_obj["calorie"] = int(float(menu_obj["CALORIE_QY"]))
    new_obj["carbohydrate"] = float(menu_obj["CARBOH_QY"])
    new_obj["protein"] = float(menu_obj["PROTEIN_QY"])
    new_obj["fat"] = float(menu_obj["FAT_QY"])
    new_obj["materials"] = parse_matrl_name(menu_obj["MATRL_NM"])
    new_obj["recipe"] = menu_obj["COOK_MTH_CONT"].split("<br>")
    new_obj["need"] = menu_obj["MATRL_NM"]
    return new_obj
