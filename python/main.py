from typing import Union
from fastapi import FastAPI
from pymongo.mongo_client import MongoClient
from pymongo.server_api import ServerApi
import uvicorn
from pydantic_settings import BaseSettings
class Settings(BaseSettings):
    MONGO_USERNAME : str
    MONGO_PASSWORD : str
    MONGO_HOST: str


settings = Settings()
app = FastAPI()

uri = f'mongodb+srv://{settings.MONGO_USERNAME}:{settings.MONGO_PASSWORD}{settings.MONGO_HOST}'
client = MongoClient(uri, server_api=ServerApi('1'))
try:
    client.admin.command('ping')
    print("Pinged your deployment. You successfully connected to MongoDB!")
except Exception as e:
    print(e)

@app.get("/hello")
def hello_world(user_id : int | None = None):
    return {"Hello": "World"}

@app.get("/mongo/info")
async def get_info():
    return {
        "mongo_username" : settings.MONGO_USERNAME,
        "mongo_password" : settings.MONGO_PASSWORD,
        "mongo_host" : settings.MONGO_HOST
    }

