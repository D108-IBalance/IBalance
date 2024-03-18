from pymongo.mongo_client import MongoClient
from pymongo.server_api import ServerApi
from bson.objectid import ObjectId

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

def validation_check(collection_name : None):
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

def find_by_object_id(collection_name, _id : str):
    result = dict()
    if not validation_check(collection_name):
        return result
    collection = client[DATABASE_NAME][collection_name]
    cursor = collection.find({"_id" : ObjectId(_id)})
    for document in cursor:
        _id = str(ObjectId(document["_id"]))
        del document["_id"]
        document["menu_id"] = _id
        result = document
    return result



