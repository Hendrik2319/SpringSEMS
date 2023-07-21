docker run --rm -d^
 --name sems-server^
 --network sems_mysqlnet^
 -p 8080:8080^
 -e MYSQL_URL=jdbc:mysql://sems_mysqlserver:3306/db_sems^
 -e MYSQL_USER=sems_app^
 -e MYSQL_PASS=sems_password^
 sems-docker