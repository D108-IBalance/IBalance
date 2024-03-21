import mysql.connector

mysql_client = None
last_host = None
last_user = None
last_password = None
last_database = None

def mysql_connect(host, user, password, database):
    global mysql_client
    mysql_client = mysql.connector.connect(
        host=host,
        user=user,
        password=password,
        database=database
    )
    if mysql_client.is_connected():
        print("mysql connected!!!")

def mysql_validation_check():
    global mysql_client
    global last_host, last_user, last_password, last_database
    if mysql_client is None or not mysql_client.is_connected():
        print("mysql_client is empty, mysql reconnect...")
        mysql_connect(last_host, last_user, last_password, last_database)

def find_all(table_name):
    mysql_validation_check()
    global mysql_client
    cur = mysql_client.cursor()
    sql = '''SELECT * FROM `{}` WHERE 1=1'''.format(table_name)
    print(f'preparing sql: {sql}')
    cur.execute(sql)
    result = cur.fetchall()
    cur.close()
    return result

def update_random_menu_id(table_name, pk, change_id):
    mysql_validation_check()
    global mysql_client
    cur = mysql_client.cursor()
    sql = '''UPDATE `{}` set menu_id="{}" where id={}'''.format(table_name, change_id, pk)
    print(f'prepareing sql: {sql}')
    cur.execute(sql)
    mysql_client.commit()
    cur.close()
