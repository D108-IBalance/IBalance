from pydantic_settings import BaseSettings
from pymongo.mongo_client import MongoClient
from pymongo.server_api import ServerApi
import re
import requests

settings = None
uri = None
client = None
db = None

class Settings(BaseSettings):
    MONGO_HOST: str


def _init():
    global uri
    global settings
    uri = None
    settings = None

def get_environment_variables():
    global settings
    settings = Settings()
    print("반갑습니다.")
    print(settings)

def connect_to_mongo():
    global client
    global uri
    global settings
    print(settings)
    settings = Settings()

    uri = f'{settings.MONGO_HOST}'
    client = MongoClient(uri, server_api=ServerApi('1'))
    try:
        client.admin.command('ping')
        print("Pinged your deployment. You successfully connected to MongoDB!")
    except Exception as e:
        print(e)
def get_mat_list():

    mat_str = "두부, 버섯, 당근, 가지, 달걀, 깻잎, 브로콜리, 시금치, 토마토, 고구마, 아스파라거스, 치즈, 오이, 배, 양배추, 마늘, 양파, 단호박, 옥수수, 새우, 멸치, 전복, 파프리카, 비트, 오이피클, 애호박, 오렌지, 레몬, 연어, 대추, 잣, 다슬기, 부추, 된장, 파, 건다시마, 오징어, 감자, 취나물, 고사리, 달래, 피망, 방울토마토, 호두, 딸기, 쑥, 시래기, 생강, 생크림, 다래, 가자미, 꽃게, 아몬드, 어묵, 떡볶이떡, 조개, 오징어, 청경채, 우유, 콩나물, 펜네, 로즈마리, 돼지목살, 샐러리, 포도주스, 버터, 닭고기, 베이컨, 햄, 보리, 율무, 은행, 팥, 로즈마리, 두릅, 숙주, 파인애플, 곤약, 땅콩, 완두콩, 고구마, 갈치, 고등어, 낙지, 주꾸미, 조기, 연근, 홍시, 김치, 장어, 더덕, 카레"
    mat_cate = mat_str.split(", ")
    print(mat_cate)
    return mat_cate
def get_api_data():
    start = 0
    end = 10
    res = requests.get("https://openapi.foodsafetykorea.go.kr/api/00ed7b73125842ccad6b/COOKRCP01/json/0/1")
    json = res.json()
    mat_list = get_mat_list()
    print(f'mat_list : {mat_list}')
    inner = json['COOKRCP01']
    total_count = int(inner['total_count'])
    data_list = inner['row']
    res_list = []
    menu_cate = set()
    need = ["RCP_PARTS_DTLS", "RCP_NM", "RCP_NA_TIP"]
    fork_str = ["돼지고기", "돼지목살", "목살", "돼지등심", "등심", "쪽갈비"]
    insane_str_map = { "브로컬리" : "브로콜리"}

    beef_str = ["소고기","쇠고기", "L.A갈비", "안심"]
    chick_str =["닭날개", "닭가슴살", "닭"]
    for obj in data_list:
        print(obj)
        newObj = dict()
        newObj["recipe"] = dict()
        newObj["recipe"]["content_list"] = list()
        newObj["nutrient"] = dict()
        newObj["material"] = list()
        for i in range(0,20):
            newObj["recipe"]["content_list"].append(dict())
        for key, value in obj.items():
            if key == "RCP_NM":
                newObj["menu_name"] = value
            if key == "RCP_PARTS_DTLS":
                str = "test str"
                value = value.replace('\n', ', ')
                newObj["recipe"]["need"] = value
                value = value.replace(" ", "")
                print(f'value : {value}')
                mat_set = set()
                test = "deeeeeeeeeef"
                test = test.replace("i", "z")
                print(test)
                for (key, val) in insane_str_map.items():
                    value = value.replace(key, val)
                for mat in mat_list:
                    pattern = re.compile(rf'{re.escape(mat)}\s*\(')
                    if pattern.search(value):
                        print(f'걸리지니 ? : {value}')
                        mat_set.add(mat)
                for s in fork_str:
                    pattern = re.compile(rf'{re.escape(s)}\s*\(')
                    if pattern.search(value):
                        mat_set.add(s)
                for s in chick_str:
                    pattern = re.compile(rf'{re.escape(s)}\s*\(')
                    if pattern.search(value):
                        mat_set.add(s)
                for s in beef_str:
                    pattern = re.compile(rf'{re.escape(s)}')
                    if pattern.search(s):
                        mat_set.add(s)
                newObj["material"] = list(mat_set)
                v_list = value.split(",")
                # for str in v_list:
                #     start_s_index = str.find("(")
                #     end_s_index = str.find(")")
                #     if start_s_index != -1 and end_s_index != -1:
                #         str = str[:start_s_index]
                #     start_m_index = str.find("[")
                #     end_m_index = str.find("]")
                #     print(str)
                #     if start_m_index != -1 and end_m_index != -1:
                #         str = str[end_m_index+1:]
                #         mat_list.append(str)

            if key[:6]=="MANUAL":
                if len(key) == 8:
                    number = int(key[6:])
                    newObj["recipe"]["content_list"][number-1]["content"] = value
                else:
                    number = int(key[10:])
                    newObj["recipe"]["content_list"][number-1]["img"] = value
            if key == "RCP_PAT2":
                newObj["type"] = value
                menu_cate.add(value)
            if key == "INFO_CAR":
                newObj["nutrient"]["carbohydrate"] = value
            if key == "INFO_PRO":
                newObj["nutrient"]["protein"] = value
            if key == "INFO_FAT":
                newObj["nutrient"]["fat"] = value
        print(f'materials : {newObj["material"]}')
        print("==============================")

        newList = list()
        for idx in range(len(newObj["recipe"]["content_list"])):
            d = dict()
            if newObj["recipe"]["content_list"][idx]["content"]:
                d["content"] = newObj["recipe"]["content_list"][idx]["content"]
                d["img"] = newObj["recipe"]["content_list"][idx]["img"]
                newList.append(d)
        del newObj["recipe"]["content_list"]
        newObj["recipe"]["content_list"] = newList
        res_list.append(newObj)
    # print(res.json())
    # print(res_list)
    print(menu_cate)
    print(mat_list)
    return res_list

def insert_to_mongo(data_list: list, db_name: str, collection_name: str):
    db = client[db_name]
    db[collection_name].drop()
    collection = db[collection_name]
    collection.insert_many(data_list)


if __name__ == "__main__":
    _init()
    get_environment_variables()
    connect_to_mongo()
    data_list = get_api_data()
    #insert_to_mongo(data_list, db_name="test", collection_name="recipe")
