docker cp setup.sql tasks_oracle_1:/
docker exec tasks_oracle_1 bash -c "source /home/oracle/.bashrc; sqlplus sys/Oradoc_db1 as sysdba @setup"