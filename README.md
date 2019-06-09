# Wallet HUB - Java and MySQL Test

The goal of this project is provide a complete solution for the proposed assignment for the Senior Backend Engineer for WalletHub.

## Structure

 * [Problem Statement](./problem_statement.md)
 * Deliverables: 
    * **Java program that can be run from command line** - [parser](./dist/parser.jar)
    * **Source Code for the Java program** - [Parser project ](./parser)
    * **MySQL schema used for the log data** - [SQL folder] (./sql)
    * **SQL queries for SQL test** 
        1. [Write MySQL query to find IPs that mode more than a certain number of requests for a given time period](./sql/sql_to_find_ips_with_more_requests.sql)
        2. [Write MySQL query to find requests made by a given IP](./sql/sql_to_find_access_by_ip.sql)

## General explanation

A spring-boot application was built to solve the problem. Using spring-data to access the mysql database to manipulate two entities responsibles to handle all the data requirements:  
 * BlockedIp - Entity that stores the blocked ips
 * HttpAccess - Entity that stores all the log entries provided via access.log file
 
### Main services:
The application classes structure is summarized by the following package division:
 * model - Stores all entities and related classes, such Duration enumeration and InputPArametersDto to deliver all the input parameters to the services;
 * repository - Stores the repositories for the two JPA entities;
 * runner - Has only the CommandLineRunner class responsible to handle all the parameters passed via CommandLine
 * service - Has the services responsible to parsing the access log files, implement validation rules and the business logic to find the correct ips and block them.

 ### Parametrization 

 The application is built to be executed with the following command:

 ```
 java -jar parser.jar --accesslog=../access.log --startDate=2017-01-01.15:00:00 --duration=hourly --threshold=200
 ```

 It's not needed to pass the main class to be executed as spring maven plugin sets this on jar manifest file. 

 There are a few attributes configured under application.properties that can be customized via environment variables:

| Environment Variable Name | Defaul Value |
|---------------------------|--------------|
| DB_HOST                   | localhost    |
| DB_PORT                   | 3306         |
| DB_NAME                   | http_access  |
| DB_USERNAME               | admin        |
| DB_PASSWORD               | admin        |

### Database creation
If we set an user with enought grant on the database the application should be able to create the database if it does not exists.

## Build 

To build the application from scratch run the following command under the parser folder:

```
mvn package
```

This command will build the code, run the unit tests and package the application into a file under dist folder called parser.jar.
