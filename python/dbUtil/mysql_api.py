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