import csv
from pydantic_settings import BaseSettings
from pymongo.mongo_client import MongoClient
from pymongo.server_api import ServerApi

NAME_LIST = ["menu"]
settings = None
uri = None
client = None
db = None

class Settings(BaseSettings):
    MONGO_HOST: str
    DATABASE_NAME : str

def make_dict(keys):
    d = dict()
    for key in keys:
        d.setdefault(key, None)
    return d


def get_environment_variables():
    global settings
    settings = Settings()
    print("반갑습니다.")
    print(settings)

def make_csv_path(collection):
    return f'{collection}.csv'


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


def _init():
    global uri
    global settings
    uri = None
    settings = None


def insert_data(data_list, collection):
    collection.insert_many(data_list)


def read_csv(path):
    f = open(path, 'r', encoding='utf-8')
    is_subject = True
    lines = csv.reader(f)
    keys = []
    result = []
    for line in lines:
        if is_subject:
            for title in line:
                keys.append(title)
            is_subject = not is_subject
        prepare_data = make_dict(keys)
        for idx in range(0, len(keys)):
            prepare_data[keys[idx]] = line[idx]
        result.append(prepare_data)
    return result

def db_init():
    global client
    global db
    target = settings.DATABASE_NAME
    present_db_names = client.list_database_names()
    for present_db_name in present_db_names:
        if present_db_name == target:
            present_db_name.drop()
            break;
    db = client[settings.DATABASE_NAME]

def collection_init(collection):
    db = client[settings.DATABASE_NAME]
    present_db_collections = db.list_collection_names()
    for db_collection in present_db_collections:
        if db_collection == collection:
            db.drop_collection(db_collection)
            break

    present_cols = db[collection]
    return present_cols


if __name__ == "__main__":
    _init()
    get_environment_variables()
    connect_to_mongo()
    # db_init() database 자체를 초기화 하는 마법 !
    for NAME in NAME_LIST:
        col = collection_init(NAME)
        csv_name = make_csv_path(NAME)
        _data = read_csv(csv_name)
        insert_data(_data, col)
