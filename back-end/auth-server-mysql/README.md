# Dinner and a Movie Java

## Authentication server

The authentication server provides a default database of user accounts for authenticating with the Dinner and a Movie Application.

This application requires a MySQL or MariaDB database to be made available on port 3336 which is launched from the [daamdb folder docker-compose.yaml file](../daamdb/docker-compose.yml).

This server is only responsible for checking the users in the daamdb database and generating the JWT to be passed by your application to access all services in the **daam** resource server.

## The users

| Username | Password |
| -------- | -------- |
| admin    | password     |
| cmac     | password     |
| me       | password     |
| server1  | password     |
| server 2 | password     |

All passwords are BCrypted in the database, so when you write your part to add users ensure the passwords are saved BCrypted.

A nice web site that encrypts using BCrypt - [https://bcrypt-generator.com/](https://bcrypt-generator.com/)

## Starting this service

1. Run the docker-compose command to launch the database as follows;
    ```
    cd ../daamdb
    docker-compose up -d
    cd ../auth-server-mysql
    ```

2. Now run this service, remember to keep the window open, unless using Docker containers
    ```
    ./mvnw clean spring-boot:run
    ```

    Remember to leave this terminal running and launch the resource server in a different terminal.

> [!NOTE] 
> The authentication service launches on port 9000 by default and is set in the **application.properties** file.