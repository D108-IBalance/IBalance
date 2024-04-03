from fastapi import HTTPException

"""
@Author: 김회창
"""

"""
서비스 레이어에서 보여줄 데이터가 없을 때 감지하는 Exception
"""


class NotFoundException(HTTPException):

    def __init__(self, detail: str):
        super().__init__(status_code=404, detail=detail)


"""
컨트롤러 레이어에서 request body, path variable, query parameter에 대한 validation checker에서 발생하는 Exception
"""


class BodyValidationException(HTTPException):

    def __init__(self, msg_list: list[str]):
        self.msg_list = msg_list
        super().__init__(status_code=422, detail="body validation exception")


"""
반복된 추천등의 요인으로 인해 더이상 추천해줄 수 없을 때 발생하는 Exception
"""


class CacheExceedException(HTTPException):

    def __init__(self, detail: str):
        self.detail = detail
        super().__init__(status_code=416, detail=detail)
