#!/bin/sh

docker rm oracle -f
docker volume rm oracle

docker rm postgres -f
docker volume rm postgres

docker rm spring -f
docker rmi spring

docker rm tomcat1 -f

docker rm tomcat2 -f

docker rm wildfly -f

docker rm angular -f

echo press enter to exit
read

