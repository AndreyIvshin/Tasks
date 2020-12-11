#!/bin/sh

##ojdbc8
#mvn install:install-file -Dfile=ojdbc8.jar -DgroupId=com.oracle.ojdbc -DartifactId=ojdbc8 -Dversion=12.2.0.1 -Dpackaging=jar

##pull
#docker pull store/oracle/database-enterprise:12.2.0.1
#docker pull postgres:13
#docker pull tomcat:jdk8
#docker pull openjdk:8
#docker pull jboss/wildfly:21.0.0.Final
#docker pull nginx:1.19

#oracle
docker run --name oracle -d -p 1521:1521 -p 5500:5500 -e DB_SID=oracle -v oracle:/ORCL store/oracle/database-enterprise:12.2.0.1

health=$(docker inspect --format '{{json .State.Health.Status}}' oracle)

starting='"starting"'

while [ $health == $starting ]; do
    echo waiting for oracle database
    sleep 10
    health=$(docker inspect --format '{{json .State.Health.Status}}' oracle)
done

docker cp setup.sql oracle:/
docker exec oracle bash -c "source /home/oracle/.bashrc; sqlplus sys/Oradoc_db1 as sysdba @setup"
docker exec -u root oracle rm setup.sql

#postgres
docker run --name postgres -d -p 5432:5432 -e POSTGRES_USER=root -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=postgres  -v postgres:/var/lib/postgresql/data postgres:13

#tomcat1
mvn -f Task1/ clean package
docker run --name=tomcat1 -d -p 8081:8080 tomcat:jdk8
docker cp Task1/target/ROOT.war tomcat1:/usr/local/tomcat/webapps/ROOT.war

#tomcat2
mvn -f Task2/ clean package
docker run --name=tomcat2 -d -p 8082:8080 tomcat:jdk8
docker cp Task2/target/ROOT.war tomcat2:/usr/local/tomcat/webapps/ROOT.war

#spring
mvn -f Task3/ clean package
docker build Task3/ -t spring
docker run --name=spring -d -p 8083:8080 spring

#wildfly
mvn -f Task4/backend/ clean package
docker run --name wildfly -d -p 8085:8080 -p 9990:9990 jboss/wildfly:21.0.0.Final
docker cp Task4/backend/ojdbc8.jar wildfly:/opt/jboss/wildfly/standalone/deployments/ojdbc8.jar
docker cp Task4/backend/default-ds.xml wildfly:/opt/jboss/wildfly/standalone/deployments/default-ds.xml
docker cp Task4/backend/target/ROOT.war wildfly:/opt/jboss/wildfly/standalone/deployments/ROOT.war

#angular
pushd Task4/frontend
npm install
ng build --prod
popd
docker run --name angular -d -p 8084:80 nginx:1.19
docker cp -a Task4/frontend/dist/frontend/. angular:/usr/share/nginx/html

#end
echo press enter to exit
read

