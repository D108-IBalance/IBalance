from typing import Union
from fastapi import FastAPI
from pymongo.mongo_client import MongoClient
from pymongo.server_api import ServerApi
import uvicorn
app = FastAPI()

uri = "mongodb+srv://haechang:1234@ibalance.riz077b.mongodb.net/?retryWrites=true&w=majority&appName=ibalance"
# Create a new client and connect to the server
client = MongoClient(uri, server_api=ServerApi('1'))
# Send a ping to confirm a successful connection
try:
    client.admin.command('ping')
    print("Pinged your deployment. You successfully connected to MongoDB!")
except Exception as e:
    print(e)

@app.get("/hello")
def hello_world(user_id : int | None = None):
    return {"Hello": "World"}

if __name__ == "__main__":
    uvicorn.run(app, host="localhost", port=5050, log_level="debug")