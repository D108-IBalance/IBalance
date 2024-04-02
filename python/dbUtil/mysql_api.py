import mysql.connector
from pydantic_settings import BaseSettings
"""
@Author: 김회창
"""


class Settings(BaseSettings):
    MYSQL_HOST: str  # MySQL 호스트 주소
    MYSQL_USER: str  # MySQL 접속 유저 명
    MYSQL_PASSWORD: str  # MySQL 패스워드
    MYSQL_DATABASE: str  # MySQL 접속 대상 스키마
    MYSQL_PORT: str  # MYSQL 접속 포트


settings = Settings()  # 클래스 객체 생성

mysql_client = None # mysql 클라이언트 객체


"""
mysql 접속 클라이언트를 하나로 유지시켜주는 함수
:param: host str, mysql host주소
:param: user str, mysql 접속 유저 정보
:param: passowrd str, mysql 접속 유저 패스워드
:param: database str, mysql 접속하는 데이터 베이스 이름
:param: port int, mysql 접속 port 번호
"""


def mysql_connect():
    global mysql_client
    mysql_client = mysql.connector.connect(
        host=settings.MYSQL_HOST,
        user=settings.MYSQL_USER,
        password=settings.MYSQL_PASSWORD,
        database=settings.MYSQL_DATABASE,
        port=settings.MYSQL_PORT,
    )
    if mysql_client.is_connected():
        print("mysql connected!!!")


"""
mysql 접속 클라이언트가 유효한지 검사하는 함수
쿼리 실행 전 현재 클라이언트의 접속이 유효하지 않다면 재접속을 시도함
:return: mysql cursor 객체
"""


def mysql_validation_check() -> mysql.connector:
    global mysql_client
    if mysql_client is None or not mysql_client.is_connected():
        print("mysql_client is None or disconnected, reconnecting to MySQL...")
        mysql_connect()
        if not mysql_client.is_connected():
            mysql_client.ping(reconnect=True, attempts=3, delay=1)
        return mysql_client.cursor()



"""
private 함수로 알맞은 함수로부터 만들어진 쿼리를 validation 체크 후, 실제로 mysql에 실행시키는 함수로 잇는 역할을 수행
select 문과 그 밖의 DML 문에 따라 구분지어 실행시키는 함수를 호출
:param: query str, 실행시킬 쿼리문
:return: dictionary or list[dict]
"""


def _execute(query: str):
    cursor = mysql_validation_check()
    print(f'preparing mysql sql: {query}')
    if query in ["UPDATE", "INSERT", "DELETE"]:
        _execute_and_commit(query, cursor)
    else:
        return _execute_and_fetchall(query, cursor)


"""
private 함수로 _execute 로 부터 넘겨 받은 쿼리와 커서객체를 바탕으로 UPDATE 혹은 INSERT 혹은 DELETE문을 실제로 실행시키는 함수
:param: query str, 실행시킬 쿼리문
:param: cursor mysql.connector, mysql 커서 객체
"""


def _execute_and_commit(query: str, cursor: mysql.connector):
    global mysql_client
    cursor.execute(query)
    mysql_client.commit()
    cursor.close()


"""
private 함수로 _execute 로 부터 넘겨 받은 쿼리와 커서객체를 바탕으로 SELECT문을 실제로 실행시키는 함수
:param: query str, 실행시킬 쿼리문
:param: cursor mysql.connector, mysql 커서 객체
:return: query를 실행시킨 결과를 리턴
"""


def _execute_and_fetchall(query: str, cursor: mysql.connector) -> list[tuple]:
    global mysql_client
    cursor.execute(query)
    result = cursor.fetchall()
    cursor.close()
    return result


"""
제외시킬 id리스트를 갖고서 모든 메뉴에 대한 유저별 평점을 구하는 함수
:param: exclude_id_list list[str], 제외시킬 menu_id 리스트
:return: query를 실행시킨 결과를 리턴
"""


def find_all_rating(child_id: int, exclude_id_list: list[str] = []) -> list[tuple]:
    sql_head = '''SELECT c.id, m.menu_id, round(avg(m.score),1) as score FROM `diet-menu` AS m INNER JOIN diet AS d ON m.diet_id = d.id '''
    sql_middle_option = '''INNER JOIN child AS c ON d.child_id = c.id WHERE 1=1 AND is_reviewed = 1 '''
    sql_for_all_additional_join_condition = 'AND {} '.format("d.diet_date BETWEEN DATE_ADD(NOW(), INTERVAL -2 WEEK) AND NOW()")
    sql_for_not_me = '''AND c.id != '{}' '''.format(child_id)
    sql_for_me = '''AND c.id = '{}' '''.format(child_id)
    aditional_sql = ''
    group_by = '''group by c.id, menu_id '''
    if exclude_id_list is not None and len(exclude_id_list) > 0:
        aditional_sql += '''AND m.menu_id NOT IN ('{}') '''.format("', '".join(map(str, exclude_id_list)))
    prepared_sql = sql_head + sql_middle_option + sql_for_not_me + aditional_sql + group_by
    result_all = _execute(prepared_sql)
    prepared_sql = sql_head + sql_for_all_additional_join_condition + sql_middle_option + sql_for_me + aditional_sql + group_by
    result_me = _execute(prepared_sql)
    result = result_all + result_me
    for (user_id, menu_id, rating) in result:
        if user_id == 1:
            print("레이팅 데이타에 user_id 가 1인게 있어요!")
            break
    return result_all + result_me
