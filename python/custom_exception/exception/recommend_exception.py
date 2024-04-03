"""
@Author: 김회창
"""

"""
더이상 추천 할 메뉴가 없을 때 recommend.py에서 발생하는 Exception
"""


class RecommendExceedException(Exception):
    def __init__(self):
        Exception.__init__(self)
