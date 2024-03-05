from typing import Union

from fastapi import FastAPI

import uvicorn
app = FastAPI()

@app.get("/hello")
def hello_world(user_id : int | None = None):
    return {"Hello": "World"}

if __name__ == "__main__":
    uvicorn.run(app, host="localhost", port=5050, log_level="debug")