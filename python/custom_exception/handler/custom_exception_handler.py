from starlette.requests import Request
from fastapi.responses import JSONResponse
from custom_exception.exception.custom_http_exception import NotFoundException, BodyValidationException
import json

"""
@Author: 김회창
"""

"""
request body의 json을 파이썬의 딕셔너리로 변환하는 함수
:param: request HTTPConnection, request body
:return: dict, json -> dict 로 변환된 결과물
"""


async def _json_to_dict(request: Request) -> dict:
    body_bytes = await request.body()
    return json.loads(body_bytes)

"""
NotFoundException 에 대하여 대응되는 handler 함수
:param: request HTTPConnection, request body
:param: exception NotFoundException, 발생한 exception 객체
:return: json, httpstatus 및 어떤 exception이 발생하였는지에 따른 메세지를 json으로 반환
"""


async def not_found_exception_handler(request: Request, exception: NotFoundException) -> JSONResponse:
    return JSONResponse(
        status_code=exception.status_code,
        content={"msg": exception.detail}
    )


"""
BodyValidationException 에 대하여 대응되는 handler 함수
:param: request HTTPConnection, request body
:param: exception BodyValidationException, 발생한 exception 객체
:return: json, httpstatus 및 어떤 exception이 발생하였는지에 따른 메세지를 json으로 반환
"""


async def body_validation_exception_handler(request: Request, exception: BodyValidationException) -> JSONResponse:
    return JSONResponse(
        status_code=exception.status_code,
        content={"msgList": exception.msg_list}
    )
