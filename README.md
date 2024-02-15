# Step by step how to run it:
## Install docker
1. Fastest way is to download and install Docker Desktop [link](https://www.docker.com/products/docker-desktop/)
2. After launching the Docker Desktop, open CMD as admin and insert this command  
`docker run -d --name mysql-container -e MYSQL_ROOT_PASSWORD=aaa -e MYSQL_DATABASE=your_database_name -e MYSQL_USER=user -e MYSQL_PASSWORD=bbb -p 3306:3306 mysql:latest`
3. This should download a mysql docker image (if it's not already installed) and run it with some basic arguments
4. Next time you want to run it, just launch Docker Desktop go to Containers and Start the mysql container 
## Run project
1. Install IntelliJ IDEA [link](https://www.jetbrains.com/idea/download/)
2. Pull this project by going to *File -> New -> Project from Version Controll...* and inserting URL `https://github.com/j99wozniak/sprproj.git`
3. Make sure SDK in *File -> Project Structure -> Project* is set to 21
4. In sprproj\src\main\resources\application.properties set dataaccessor to DBService to use mysql docker container as database. Set it to anything else to use local hashmap that behaves like a database
5. Build and run
6. Run tests (the mysql docker container needs to be running, regardless if the data accessor uses mysql docker or local hashmap)
    * ApiControllerTest are basic tests to see if the app builds, and presents an accessible api
    * DbViaApiTest are integration tests that actually check if the interaction with db/memory works while using the presented api
    * ValidatorTest are unit tests that check the functionality of the validator
7. Use services like Postman ([link](https://www.postman.com/downloads/)) to make HTTP requests. The app doesn't have an UI, just REST API.
8. To make sure that the app works, send this request:  
`http://localhost:8080/api/hello`
## Endpoints
There are two users defined, Aleander/pass (with user role) and admin/pass! (with admin and user roles).
They both use basic authentication.
### Basic usage
 * [Get] [ANYONE] /api/hello - just to check the connection
 * [Get] [user] /api/user - needs user basic authentication. Used to check authentication and role
 * [Get] [user] /api/property - used to get value of a property `http://localhost:8080/api/property?name=dataaccessor`
### Data manipulation
 * [Post] [admin] /api/settablename - first set the name of the table we want to work with `http://localhost:8080/api/settablename?name=my_data`
 * [Post] [admin] /api/createtable - create the table with the name that has been set
 * [Post] [admin] /api/create - create a record in the table `http://localhost:8080/api/create` and then in the body as raw JSON `{ "name": "Astro", "age": 13 }`
 * [Get] [user] /api/show - show records that meet all of the parameters `http://localhost:8080/api/show?name=astro&age=13`, no parameters show all records in the table
 * [Patch] [admin] /api/change - change name and/or age of the record of the provided id `http://localhost:8080/api/change?id=1&age=15`
 * [Delete] [admin] /api/delete - delete record of the provided id `http://localhost:8080/api/delete?id=1`
 * [Delete] [admin] /api/deletetable - delte the whole table of the name that has been set
