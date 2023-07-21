1. create volumes  
`docker volume create sems_mysql_data`  
`docker volume create sems_mysql_config`

1. create a network  
`docker network create sems_mysqlnet`

1. start MySQL server
`docker run -it --rm -d \`  
`-v sems_mysql_data:/var/lib/mysql \`  
`-v sems_mysql_config:/etc/mysql/conf.d \`  
`--network sems_mysqlnet \`  
`--name sems_mysqlserver \`  
`-e MYSQL_DATABASE=db_sems \`  
`-e MYSQL_ROOT_PASSWORD=root \`  
`-e MYSQL_USER=sems_app \`  
`-e MYSQL_PASSWORD=sems_password \`  
`-p 3306:3306 mysql:8.0`  
`docker run -it --rm -d -v sems_mysql_data:/var/lib/mysql
-v sems_mysql_config:/etc/mysql/conf.d
--network sems_mysqlnet
--name sems_mysqlserver
-e MYSQL_DATABASE=db_sems
-e MYSQL_ROOT_PASSWORD=root
-e MYSQL_USER=sems_app
-e MYSQL_PASSWORD=sems_password
-p 3306:3306 mysql:8.0`  

1. build SEMS image  
`docker build -f Dockerfile.single --tag sems-docker .`

1. run image as container  
`docker run --rm -d
--name sems-server
--network sems_mysqlnet
-p 8080:8080
-e MYSQL_URL=jdbc:mysql://sems_mysqlserver:3306/db_sems
-e MYSQL_USER=sems_app
-e MYSQL_PASS=sems_password
sems-docker`

1. run compose
`docker-compose -f docker-compose.dev.yml up --build`