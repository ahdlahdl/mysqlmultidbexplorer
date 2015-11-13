# mysqlmultidbexplorer
multiple database query

# mvn clean compile assembly:single

# usage.

1. 실행할 쿼리를 파일로 작성.
cat query.sql
"
select NOW(), RAND() from dual;
"

2. 쿼리를 실행할 db 커넥션 정보를 파일로 작성.
cat jdbc.properties
"
jdbc:mysql://localhost:3306/db1
jdbc:mysql://localhost:3306/db2
jdbc:mysql://localhost:3306/db3
username=root
password=mydbpassword
"

3. 쿼리 실행.
java -jar mysqlmultidbexplorer.jar query.sql
