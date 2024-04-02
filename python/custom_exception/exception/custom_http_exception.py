from fastapi import HTTPException

"""
@Author: 김회창
"""


class NotFoundException(HTTPException):

    def __init__(self, detail: str):
        super().__init__(status_code=404, detail=detail)


class BodyValidationException(HTTPException):

    def __init__(self, msg_list: list[str]):
        self.msg_list = msg_list
        super().__init__(status_code=422, detail="body validation exception")


class CacheExceedException(HTTPException):

    def __init__(self, detail: str):
        self.detail = detail
        super().__init__(status_code=416, detail=detail)
