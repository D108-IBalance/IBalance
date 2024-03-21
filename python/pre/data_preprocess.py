from bson.objectid import ObjectId

"""
@Author: 김회창
"""


def menu_pre(menu_obj):
    if "_id" not in menu_obj.keys():
        return menu_obj
    menu_obj["menu_id"] = str(ObjectId(menu_obj["_id"]))
    del menu_obj["_id"]
    return menu_obj

def filter_menu(exclude_mat_list: list, prev_menu_id_list: list, menu):
    if type(menu) != dict:
        print("parameter menu must list")
        return menu
    for idx in range(0, len(menu)):
        if menu[idx]["MATRL_NM"] in exclude_mat_list:
            del menu[idx]





