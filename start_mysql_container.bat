docker run -it --rm -d^
 -v mysql_data:/var/lib/mysql^
 -v mysql_config:/etc/mysql/conf.d^
 --network sems_mysqlnet^
 --name sems_mysqlserver^
 -e MYSQL_DATABASE=db_sems^
 -e MYSQL_ROOT_PASSWORD=root^
 -e MYSQL_USER=semsapp^
 -e MYSQL_PASSWORD=semspassword^
 -p 3305:3306^
 mysql:8.0