from fastapi import FastAPI, Query, Body, Path
from custom_exception.exception.custom_http_exception import NotFoundException, BodyValidationException
from custom_exception.handler.custom_exception_handler import not_found_exception_handler, \
    body_validation_exception_handler
from dbUtil.mongodb_api import mongodb_connect
from dbUtil.mysql_api import mysql_connect
from request.request_dto import ChildInfo, DietOfMenuId, PickyWithAllergy
from service import diet_init, menu_info, one_diet_recommend, new_menu_recommend, get_menu_names_by_ids, menu_infos, \
    picky_recipes, picky_recipe, picky_main
from request.validation_check import child_info_request_validation_checker, object_id_validation_checker, \
    diet_of_menu_id_validation_checker, menu_id_list_validation_checker, picky_detail_validation_checker, \
    picky_with_allergy_validation_check
import warnings

# 특정 유형의 경고를 필터링
warnings.filterwarnings("ignore", category=RuntimeWarning)

"""
@Author : 김회창
"""

app = FastAPI()  # Fast API 앱 객체 생성

app.add_exception_handler(NotFoundException,
                          not_found_exception_handler)  # HTTPStatus code 404 exception에 해당하는 handler를 FastAPI app에 등록
app.add_exception_handler(BodyValidationException,
                          body_validation_exception_handler)  # HTTPStatus code 422 exception에 해당하는 handler를 FastAPI app에 등록

mongodb_connect()  # 몽고DB 연결
mysql_connect()  # MySQL 연결

"""
초창기 식단 7개를 한번에 추천해주는 컨트롤러
"""


@app.post("/recomm/init")
async def init_recommend(request: ChildInfo) -> list[list[dict]]:
    child_info_request_validation_checker(request)
    return diet_init(request)


"""
메뉴 고유id를 통해 메뉴 정보 조회하는 컨트롤러
"""


@app.get("/recomm/info/{menu_id}")
async def get_menu_info(menu_id: str = Path(description="메뉴 고유 id")) -> dict:
    wrap = dict()
    wrap["msgList"] = list()
    object_id_validation_checker(menu_id, "menu_id", msg_dict=wrap)
    return menu_info(menu_id)


"""
단일 식단 조회하는 컨트롤러
"""


@app.post("/recomm")
async def refresh_recommend(request: ChildInfo) -> list[dict]:
    child_info_request_validation_checker(request)
    return one_diet_recommend(request)


"""
식단 내 메뉴 교체하는 컨트롤러
"""


@app.post("/recomm/menu")
async def refresh_menu(request: ChildInfo) -> dict:
    child_info_request_validation_checker(request)
    return new_menu_recommend(request)


"""
요일 내 식단들의 고유 메뉴id리스트를 받아서 각 식단별 메뉴 이름 리스트를 조회하는 컨트롤러
"""


@app.post("/recomm/info/diet")
async def get_menu_names(request: list[DietOfMenuId] = Body(description="각 식단별 고유 dietId와 그에 대응되는 menuId 리스트")) -> list[dict]:
    diet_of_menu_id_validation_checker(request)
    return get_menu_names_by_ids(request)


"""
식단 내 4개의 메뉴 id 리스트를 받아서 해당 메뉴의 이름, 식재료 리스트, 이미지를 제공하는 컨트롤러
"""


@app.post("/recomm/info")
async def get_menu_infos(request: list[str] = Body(description="식단 내 4개의 메뉴 id 리스트를 받아서 해당 메뉴의 이름, 식재료 리스트, 이미지를 조회")) -> \
list[dict]:
    menu_id_list_validation_checker(request)
    return menu_infos(request)


"""
편식 식재료와 알러지 정보에 따른 레시피를 페이지네이션 기능으로 제공하는 컨트롤러
"""


@app.post("/recomm/picky")
async def get_picky_recipes(
        lastid: str = Query(default=None, description="마지막으로 받았던 레시피의 recipeId"),
        offset: int = Query(10, description="페이지 당 보여줄 개수", gt=0),
        matrl: str = Query(description="편식 식재료 명"),
        request: list[str] = Body(description="알러지 이름들")
) -> list[dict]:
    wrap = dict()
    wrap["msgList"] = list()
    if lastid is not None and not lastid == "":
        object_id_validation_checker(lastid, "lastid", msg_dict=wrap)
    return picky_recipes(lastid, offset, request, matrl)


"""
편식 식재료 명, 레시피 고유 id를 받아서 해당 레시피의 정보 제공하는 컨트롤러
"""


@app.get("/recomm/picky/{matrl_name}/{recipe_id}")
async def get_picky_recipe_detail(
        matrl_name: str = Path(description="편식 식재료 명"),
        recipe_id: str = Path(description="편식 레시피 고유 id")
) -> dict:
    picky_detail_validation_checker(matrl_name, recipe_id)
    return picky_recipe(matrl_name, recipe_id)


"""
아이의 알러지 정보와 편식 식재료 정보를 받아서 편식 해결 레시피 메인페이지에 식재료 별로 5개씩 제공하는 컨트롤러
"""


@app.post("/recomm/picky/main")
async def get_picky_recipes_main(request: PickyWithAllergy) -> list[dict]:
    picky_with_allergy_validation_check(request)
    return picky_main(request)
