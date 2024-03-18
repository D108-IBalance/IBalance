from pydantic import BaseModel, Field


class Content(BaseModel):
    content: str
    img: str


class Recipe(BaseModel):
    need: str
    contentList: list[Content] = Field(..., alias="content_list")

class Nutrient(BaseModel):
    protein: str
    fat: str
    carbohydrate: str


class Diet(BaseModel):
    recipe: Recipe
    nutrient: Nutrient
    material: list[str]
    type: str
    menuName: str = Field(..., alias="menu_name")
    menuId: str = Field(..., alias="menu_id")
