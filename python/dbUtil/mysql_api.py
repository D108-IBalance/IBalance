import mysql.connector

mysql_client = None
last_host = None
last_user = None
last_password = None
last_database = None
last_port = None


def mysql_connect(host, user, password, database, port):
    global mysql_client
    mysql_client = mysql.connector.connect(
        host=host,
        user=user,
        password=password,
        database=database,
        port=port
    )
    if mysql_client.is_connected():
        print("mysql connected!!!")


def mysql_validation_check():
    global mysql_client
    global last_host, last_user, last_password, last_database, last_port
    if mysql_client is None or not mysql_client.is_connected():
        print("mysql_client is empty, mysql reconnect...")
        mysql_connect(last_host, last_user, last_password, last_database, last_port)
    return mysql_client.cursor()


def _execute(query: str):
    cursor = mysql_validation_check()
    print(f'preparing sql: {query}')
    if query in ["UPDATE", "INSERT", "DELETE"]:
        _execute_and_commit(query, cursor)
    else:
        return _execute_and_fetchall(query, cursor)


def _execute_and_commit(query: str, cursor: mysql.connector):
    global mysql_client
    cursor.execute(query)
    mysql_client.commit()
    cursor.close()


def _execute_and_fetchall(query: str, cursor: mysql.connector):
    global mysql_client
    cursor.execute(query)
    result = cursor.fetchall()
    cursor.close()
    return result


def find_all_rating(exclude_id_list=[]):
    sql = '''SELECT c.id, m.menu_id, round(avg(m.score),1) as score FROM `diet-menu` AS m INNER JOIN diet AS d ON m.diet_id = d.id INNER JOIN child AS c ON d.child_id = c.id WHERE 1=1 AND is_reviewed = 1 '''
    if exclude_id_list is not None and len(exclude_id_list) > 0:
        sql += '''AND m.menu_id NOT IN ({}) '''.format(", ".join(map(str, exclude_id_list)))
    sql += '''group by c.id, menu_id '''
    result = _execute(sql)
    return result


def update_random_menu_id(table_name, pk, change_id):
    sql = '''UPDATE `{}` set menu_id="{}" where id={}'''.format(table_name, change_id, pk)
    _execute(sql)
