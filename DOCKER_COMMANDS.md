1. create volumes  
`docker volume create sems_mysql_data`  
`docker volume create sems_mysql_config`

1. create a network  
`docker network create sems_mysqlnet`

1. start MySQL server
* `docker run -it --rm -d -v mysql_data:/var/lib/mysql \`  
`-v mysql_config:/etc/mysql/conf.d \`  
`--network sems_mysqlnet \`  
`--name sems_mysqlserver \`  
`-e MYSQL_DATABASE=db_sems \`  
`-e MYSQL_ROOT_PASSWORD=root \`  
`-e MYSQL_USER=semsapp \`  
`-e MYSQL_PASSWORD=semspassword \`  
`-p 3306:3306 mysql:8.0`  
* `docker run -it --rm -d -v mysql_data:/var/lib/mysql
-v mysql_config:/etc/mysql/conf.d
--network sems_mysqlnet
--name sems_mysqlserver
-e MYSQL_DATABASE=db_sems
-e MYSQL_ROOT_PASSWORD=root
-e MYSQL_USER=semsapp
-e MYSQL_PASSWORD=semspassword
-p 3306:3306 mysql:8.0`  
