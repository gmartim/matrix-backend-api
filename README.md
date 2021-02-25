# matrix-backend-api

### Overview

This sample intends to demonstrate my abilities understanding the requirements for the League's code challenge and implementing these requirements to a clean code, which is easy to read and understand, documented and runs unit and integration tests.

### Design Consideration

I considered an MVC design pattern approach, where I split the controller from the service layer and also tried to make every service as much specific as possible, making each of them responsible for a minimum number of tasks. It makes it easier to implement and create unit tests.

Also, for operations, I create an abstract parent class that has all the common tasks executed for every operation and the specific task for each operation was implemented on the children, where each operation has its own class extending the abstract class. This approach makes possible to re-use common methods, implementation and create unit tests.

Every operation has its own controller also, I used this approach to make code cleaner and also make it easier to create the unit tests.

### Preparation

To run it local you can use Docker or Maven (Java 11), make sure you have one of them before move forward. If you are already familiar with Docker, or even already have it installed, use this options will be easier for you.

_Docker_

```
➜  ~ docker version 
Client: Docker Engine - Community
 Cloud integration: 1.0.7
 Version:           20.10.2
 API version:       1.41
 Go version:        go1.13.15
 Git commit:        2291f61
 Built:             Mon Dec 28 16:12:42 2020
 OS/Arch:           darwin/amd64
 Context:           default
 Experimental:      true

Server: Docker Engine - Community
 Engine:
  Version:          20.10.2
  API version:      1.41 (minimum version 1.12)
  Go version:       go1.13.15
  Git commit:       8891c58
  Built:            Mon Dec 28 16:15:28 2020
  OS/Arch:          linux/amd64
  Experimental:     false
 containerd:
  Version:          1.4.3
  GitCommit:        269548fa27e0089a8b8278fc4fc781d7f65a939b
 runc:
  Version:          1.0.0-rc92
  GitCommit:        ff819c7e9184c13b7c2607fe6c30ae19403a7aff
 docker-init:
  Version:          0.19.0
  GitCommit:        de40ad0
➜  ~ 
```

_Java_

```
➜  ~ java -version
java version "11.0.9" 2020-10-20 LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.9+7-LTS)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.9+7-LTS, mixed mode)
➜  ~ 
```

_Maven_

```
➜  ~ mvn -version 
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: /Library/ApacheMaven
Java version: 11.0.9, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-11.0.9.jdk/Contents/Home
Default locale: en_BR, platform encoding: UTF-8
OS name: "mac os x", version: "10.16", arch: "x86_64", family: "mac"
➜  ~ 
```

If needed, here are some links to help to prepare your environment:

- [Get Docker](https://docs.docker.com/get-docker/)
- [How do I install Java ?](https://www.java.com/en/download/help/download_options.html)
- [Installing Apache Maven](https://maven.apache.org/install.html)

Make sure to move to the path where you have the source code, in this example it was used the _matrix-backend-api_.

```
➜  matrix-backend-api ls -l 
total 64
-rw-r--r--  1 gumar  staff    144 Feb 22 17:46 Dockerfile
-rw-r--r--@ 1 gumar  staff   1192 Feb 19 10:16 README.md
drwxr-xr-x  5 gumar  staff    160 Feb 19 07:24 e-Core's kickoff
-rwxr-xr-x@ 1 gumar  staff  10070 Feb 19 10:16 mvnw
-rw-r--r--@ 1 gumar  staff   6608 Feb 19 10:16 mvnw.cmd
-rw-r--r--@ 1 gumar  staff   1645 Feb 21 14:43 pom.xml
drwxr-xr-x@ 5 gumar  staff    160 Feb 21 11:07 src
drwxr-xr-x  9 gumar  staff    288 Feb 23 06:09 target
drwxr-xr-x  9 gumar  staff    288 Feb 22 16:26 toolbox
➜  matrix-backend-api 
```

** You can run just Maven or Docker on your local at same time, make sure to stop one before try other

### Run Local Using Docker

To run the application using Docker just run:

```
docker build -t matrix-backend-api .
docker run -p 8080:8080 matrix-backend-api
```

At the end, you will see a message similar to this:

```
2021-02-23 06:17:04.494  INFO 18677 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2021-02-23 06:17:04.503  INFO 18677 --- [  restartedMain] com.league.matrix.Application            : Started Application in 1.317 seconds (JVM running for 1.683)
```

### Run Local Using Maven

To run the application using Maven just run:

```
chmod +x mvnw
./mvnw spring-boot:run
```

At the end, you will see a message similar to this:

```
2021-02-23 06:17:04.494  INFO 18677 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2021-02-23 06:17:04.503  INFO 18677 --- [  restartedMain] com.league.matrix.Application            : Started Application in 1.317 seconds (JVM running for 1.683)
```

### Run Unit and Integration Test

If you want to run the unit and integration test just run:

```
mvn test
```

At the end, you will see a message similar to this:

```
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 66, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  6.999 s
[INFO] Finished at: 2021-02-23T06:09:41-03:00
[INFO] ------------------------------------------------------------------------
```

### Executing a Request

This section describes how to run the requests to the application using the _curl_ command, few free to use any other tool that is able to run HTTP requests. The application should be up and running before execute the requests.

#### /echo

Returns a string joining the columns with a comma (",") and rows with new line ("\n").

_request_

```
curl -X POST -F 'file=@toolbox/matrix_valid.csv' http://localhost:8080/echo
```

_response_

```
1,2,3
4,5,6
7,8,9
```

#### /flatten

Returns a string joining the columns and rows with a comma (",").

_request_

```
curl -X POST -F 'file=@toolbox/matrix_valid.csv' http://localhost:8080/flatten
```

_response_

```
1,2,3,4,5,6,7,8,9
```

#### /invert

Inverts the matrix, rows gets to columns, and returns a string joining the columns with a comma (",") and rows with new line ("\n").

_request_

```
curl -X POST -F 'file=@toolbox/matrix_valid.csv' http://localhost:8080/invert
```

_response_

```
1,4,7
2,5,8
3,6,9
```

#### /multiply

Returns a string multiplying all columns values.

_request_

```
curl -X POST -F 'file=@toolbox/matrix_valid.csv' http://localhost:8080/multiply
```

_response_

```
362880
```

#### /sum

Returns a string summing all columns values.

_request_

```
curl -X POST -F 'file=@toolbox/matrix_valid.csv' http://localhost:8080/sum
```

_response_

```
45
```

#### The CSV File

All the operations requires to receive a CSV file as request parameter, the file content must be integer values split by comma (",") for columns and split by new line ("\n") for new rows.

For example:

```
1,2,3
4,5,6
7,8,9
```

You should make sure that all the values are valid integers, _1, 2, 3, 4..._ , and none of the values is bigger then **2147483647**, it is the maximum allowed value for integers.

Also, the content should evaluate to a square matrix, where the number of columns and rows are the same.

There are few files that can be used during the tests, they are available inside **toolbox** folder:

```
➜  matrix-backend-api ls -l toolbox 
total 8520
-rw-r--r--@ 1 gumar  staff        0 Feb 21 09:29 matrix_empty.csv
-rw-r--r--  1 gumar  staff       17 Feb 21 09:24 matrix_notInteger.csv
-rw-r--r--  1 gumar  staff       26 Feb 21 09:15 matrix_notSquare.csv
-rw-r--r--  1 gumar  staff       28 Feb 23 07:10 matrix_notValidLength.csv
-rw-r--r--  1 gumar  staff       26 Feb 23 07:09 matrix_notValidValue.csv
-rw-r--r--  1 gumar  staff  3360000 Feb 21 11:13 matrix_tooLarge.csv
-rw-r--r--  1 gumar  staff       17 Feb 19 07:24 matrix_valid.csv
-rw-r--r--  1 gumar  staff    21000 Feb 21 11:14 matrix_valid_larger.csv
➜  matrix-backend-api 
```

#### Error Response

All the operations may return one of the error response if something wrong occurs.

| HTTP Status | Message | Reason |
| ----------- | ------- | ------ |
| 500 | **ANY** | An internal error occurred |
| 400 | Found not integer value | At least one of the value is not integer |
| 400 | There is a value that its length is too high and not supported, value is: [**ANY**] | One of the value's length is to big and is not an integer, **ANY** represents the value |
| 400 | There is a value that is too high and not supported, value is: [**ANY**] | One of the value's is bigger then the maximum allowed value for integer, **ANY** represents the value |
| 400 | The multipart file can not be null or empty | An empty file was used |
| 400 | The matrix is not square | The content file evaluated to a not square matrix and it is not allowed |
| 400 | ** The operation reached the maximum possible value and cannot go further | The operation reached the maximum allowed value for an integer and cannot be completely executed |
| 417 | File is too large | The application just supports 1MB file size, you will see this message when the file is bigger then that |

** just thrown from multiply and sum operations

### Change Application Configuration

If you want to change the application configuration, for example, to accept a bigger file, open **application.properties** and change the properties:

```
spring.servlet.multipart.max-file-size=1MB
spring.servlet.multipart.max-request-size=1MB
```

### Future Release

There are a few improvements I would do in the future::

- **Swagger**: implement to have a better documentation for the application
- Use **long** for multiply and sum: for both operations it would make the application able to process higher values using long instead of integer
- **Cache**: check the possibility to use cache for few operations, specially for multiply and sum, need to check if creating a key for cache based on the matrix worths to have the cache itself, maybe creating the cache key is more expansive then run the operation
- **JSON/XML** on request and response: send the file's content as base 64 inside a JSON/XML and also send back a JSON/XML with additional information, like a code for errors, a better message with details, etc
