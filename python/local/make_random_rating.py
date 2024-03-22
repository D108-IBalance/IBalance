from bson.objectid import ObjectId
import random
def get_all_menu_id(mongo_client):
    menu_id_list = list()
    menu_list = mongo_client["ibalance"]["menu"].find()
    for menu in menu_list:
        menu_id_list.append(str(ObjectId(menu["_id"])))
    return menu_id_list

def generate_random_rating():
    return round(random.uniform(0.0, 5.0), 1)

def make(mongo_client, mysql_client):
    menu_id_list = get_all_menu_id(mongo_client)
    mycursor = mysql_client.cursor()
    child_ids = list(range(1, 101))
    for _ in range(1000):
        child_id = random.choice(child_ids)
        menu_id = random.choice(menu_id_list)
        rating = generate_random_rating()
        sql = "INSERT INTO menu_rating (child_id, menu_id, rating) VALUES (%s, %s, %s)"
        val = (child_id, menu_id, rating)
        mycursor.execute(sql, val)
    mysql_client.commit()
    mycursor.close()
    # mysql_client.close()
    print(menu_id_list)

