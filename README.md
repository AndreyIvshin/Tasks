# Tasks
### Setup:
1. Install [ojdbc.jar](ojdbc8.jar) into maven local repository
    
   ```shell script
    mvn install:install-file -Dfile=ojdbc8.jar -DgroupId=com.oracle.ojdbc -DartifactId=ojdbc8 -Dversion=12.2.0.1 -Dpackaging=jar
   ```

2. Run maven command

   ```shell script
   mvn clean package
   ```

3. Run docker [containers](docker-compose.yml)

   ```shell script
   docker-compose up -d
   ```

4. To configure oracle database [user and databases](setup.sql) after oracle database 
container `(tasks_oracle_1)` becomes `healthy` run [setup.sh](setup.sh) script or run following commands
    
   ```shell script
   docker cp setup.sql tasks_oracle_1:/
   docker exec tasks_oracle_1 bash -c "source /home/oracle/.bashrc; sqlplus sys/Oradoc_db1 as sysdba @setup"
   ```

### Remove:
1. Remove docker containers networks and volumes

   ```shell script
   docker-compose down -v
   ```