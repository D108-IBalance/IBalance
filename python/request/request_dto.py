from pydantic import BaseModel


class Item(BaseModel):
    name: str
    description: list | None = None
    price: float
    tax: float | None = None
