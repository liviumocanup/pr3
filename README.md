# Web App Homework

### _Mocanu Liviu_

----

## Abstract
Project made as Homework for Endava JAVA Development Internship [Autumn 2022].

Technologies: Spring Boot with Maven and Oracle Database.

## Configuration
### Running the database in docker
Setup for the Official Oracle Container Image:
1. docker login container-registry.oracle.com (using your Oracle account credentials)
2. docker pull container-registry.oracle.com/database/standard:latest
3. docker run -d --env-file ora_db_env.dat -p <listener_port>:1521 -p <http_port>:5500 -it --name <container_name> --shm-size="8g" container-registry.oracle.com/database/standard

Parameters:  
&nbsp;&nbsp;&nbsp;**<listener_port>** is the port on host machine to map the container's 1521 port (listener port).  
&nbsp;&nbsp;&nbsp;**<http_port>** is the port on host machine to map the container's 5500 port (http service port).  
&nbsp;&nbsp;&nbsp;**<container_name>** is the container name you want to create.

**_Run command on step 3 from project's docker directory!_**

### Credentials
Credentials for **simple User** (provided in application.properties):  
*Username*: appUser  
*Password*: MyPass

Credentials for **Admin**:  
*Username*: SYS <sub>(use SYS as SYSDBA for internal login in Intellij)</sub>  
*Password*: MyPasswd123