from dbUtil.mongodb_api import find_all_data


"""
@Author: 김회창
"""


TABLE = []  # 영양소 테이블로서, 만들어진 식단에 어떤 영양소가 부족한지를 체크하기 위해 mongodb에서 영양소별 포함된 식재료 리스트를 불러옴

"""
mongodb에 연결되자마자 한번 호출하여 데이터를 유지하기 위해 사용
"""


def pre_call():
    global TABLE
    result = find_all_data("unconventional_nutrition")
    TABLE = result
    print(TABLE)
