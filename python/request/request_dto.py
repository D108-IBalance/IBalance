from pydantic import BaseModel
from typing import Optional

"""
@Author: 김회창
"""


class ChildInfo(BaseModel):
    childId: int
    allergyList: list[str]
    cacheList: Optional[list[str]]
    isObesity: bool
    needType: Optional[str]
    currentMenu: Optional[list[str]]
