docker run -it --rm -d^
 -v sems_mysql_data:/var/lib/mysql^
 -v sems_mysql_config:/etc/mysql/conf.d^
 --network sems_mysqlnet^
 --name sems_mysqlserver^
 -e MYSQL_DATABASE=db_sems^
 -e MYSQL_ROOT_PASSWORD=root^
 -e MYSQL_USER=sems_app^
 -e MYSQL_PASSWORD=sems_password^
 -p 3305:3306^
 mysql:8.0