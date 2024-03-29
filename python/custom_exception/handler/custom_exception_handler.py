from starlette.requests import Request
from fastapi.responses import JSONResponse
from custom_exception.exception.custom_http_exception import NotFoundException, BodyValidationException
import json

"""
@Author: 김회창
"""


async def _json_to_dict(request: Request) -> dict:
    body_bytes = await request.body()
    return json.loads(body_bytes)


async def not_found_exception_handler(request: Request, exception: NotFoundException) -> JSONResponse:
    body = await _json_to_dict(request)
    return JSONResponse(
        status_code=exception.status_code,
        content={"msg": exception.detail}
    )


async def body_validation_exception_handler(request: Request, exception: BodyValidationException) -> JSONResponse:
    body = await _json_to_dict(request)
    return JSONResponse(
        status_code=exception.status_code,
        content={"msgList": exception.msg_list}
    )
