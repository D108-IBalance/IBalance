from pymongo.mongo_client import MongoClient
from pymongo.server_api import ServerApi
from bson.objectid import ObjectId
from pre.data_preprocess import menu_pre

DATABASE_NAME = "ibalance"
client = None
collection_name_list = []
last_uri = None


def mongodb_connect(uri):
    global client
    global collection_name_list
    global last_uri
    last_uri = uri
    if client is None:
        client = MongoClient(last_uri, server_api=ServerApi('1'))
        collection_name_list = client[DATABASE_NAME].list_collection_names()
    else:
        print("already connected")
    try:
        client.admin.command('ping')
        print("Pinged your deployment. You successfully connected to MongoDB!")
    except Exception as e:
        print(e)


def validation_check(collection_name: None):
    global client
    global collection_name_list
    global last_uri
    if collection_name is None:
        print("collection_name is empty")
        return False
    if last_uri is None:
        print("cache Error: last_uri is empty")
        return False
    if client is None:
        print("mongodb_client is empty, reconnect mongodb...")
        mongodb_connect(last_uri)
    if collection_name not in collection_name_list:
        print(f'no collection Error: parameter = {collection_name}')
        return False
    return True


def _execute(collection_name, query: dict, project: dict, is_multiple: bool):
    result = dict()
    # print('''query : {}, project {}'''.format(query, project))
    global client
    if is_multiple:
        result = list()
    if not validation_check(collection_name):
        return result

    collection = client[DATABASE_NAME][collection_name]
    if project is None:
        result = collection.find(query)
    else:
        result = collection.find(query, project)

    ret = []
    if is_multiple:
        for res in result:
            ret.append(menu_pre(res))

    else:
        ret = menu_pre(result.next())
    return ret


def find_by_object_id(collection_name, _id: str):
    query = {"_id": ObjectId(_id)}
    project = None
    result = _execute(collection_name, query, project, is_multiple=False)
    return result

def find_all_data(collection_name):
    query = {}
    project = None
    result = _execute(collection_name, query, project, is_multiple=True)
    return result

def find_all_attr(collection_name, attr_name):
    result = list()
    query = {}
    project = {
        attr_name: 1
    }
    return _execute(collection_name, query, project, is_multiple=True)

def find_attr_by_id(collection_name, attr_name, id):
    query = {
        "_id": ObjectId(id)
    }
    project = {
        attr_name : 1
    }
    return _execute(collection_name, query, project, is_multiple=False)

def find_typs(collection_name):
    global client
    global DATABASE_NAME
    client[DATABASE_NAME][collection_name].delete_many({"$expr": {"$eq": ["$MEAL_NM", "$COOK_MTH_CONT"]}})

    # print(client[DATABASE_NAME][collection_name].distinct("MEAL_CLSF_NM"))
