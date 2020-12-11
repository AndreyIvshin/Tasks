#!/bin/sh

#ojdbc8
mvn install:install-file -Dfile=ojdbc8.jar -DgroupId=com.oracle.ojdbc -DartifactId=ojdbc8 -Dversion=12.2.0.1 -Dpackaging=jar

#pull
docker pull store/oracle/database-enterprise:12.2.0.1
docker pull postgres:13
docker pull tomcat:jdk8
docker pull openjdk:8
docker pull jboss/wildfly:21.0.0.Final
docker pull nginx:1.19

#end
echo press enter to exit
read

