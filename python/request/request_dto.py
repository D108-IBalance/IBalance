from pydantic import BaseModel
from typing import Optional

"""
@Author: 김회창
"""

"""
클라이언트로 부터 넘어온 request body로서 추천 알고리즘에 영향을 줄 정보
"""


class Need(BaseModel):
    calories: int  # 필요한 칼로리 수치
    carbohydrate: float  # 필요한 탄수화물 수치
    protein: float  # 필요한 단백질 수치
    cellulose: float  # 필요한 식이섬유 수치


class ChildInfo(BaseModel):
    childId: int  # 아이의 고유번호
    allergyList: list[str]  # 알러지 정보
    cacheList: Optional[list[str]]  # 가까운 시일 내 추천받았던 혹은 제외시켜야 할 메뉴 고유번호 리스트
    need: Need  # 필요한 영양분 및 칼로리 수치
    needType: Optional[str]  # 하나의 메뉴를 추천받을 때, 해당 메뉴의 타입
    currentMenuIdOfDiet: Optional[list[str]]  # 추천 받을 식단내 메뉴를 제외한 다른 메뉴의 id 리스트


"""
diet에 대한 정보를 리턴하는 controller에 사용되는 request dto
"""


class DietOfMenuId(BaseModel):
    dietId: str  # 식단 고유 id
    menuIdList: list[str]  # 해당 식단 내 포함된 menu 고유번호 리스트


"""
편식 레시피 메인 페이지에 사용될 request dto
"""


class PickyWithAllergy(BaseModel):
    allergyNameList: list[str]  # 알러지 이름들이 문자열로 포함된 리스트
    pickyMatrlList: list[str]   # 편식 식재료 명이 문자열로 포함된 리스트
