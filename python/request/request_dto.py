from pydantic import BaseModel


class ChildInfo(BaseModel):
    childId: int
    allergyList: list[str]
    cacheList: list[str]


