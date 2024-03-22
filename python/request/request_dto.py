from pydantic import BaseModel
from typing import Optional

"""
@Author: 김회창
"""

"""
클라이언트로 부터 넘어온 request body로서 추천 알고리즘에 영향을 줄 정보
"""


class ChildInfo(BaseModel):

    childId: int    # 아이의 고유번호
    allergyList: list[str]  # 알러지 정보
    cacheList: Optional[list[str]]  # 가까운 시일 내 추천받았던 혹은 제외시켜야 할 메뉴 고유번호 리스트
    isObesity: bool # 비만 여부
    needType: Optional[str] # 하나의 메뉴를 추천받을 때, 해당 메뉴의 타입
    currentMenuIdOfDiet: Optional[list[str]]    # 추천 받을 식단내 메뉴를 제외한 다른 메뉴의 id 리스트
