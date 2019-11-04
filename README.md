# nextstep


## Steps

Guides for spring can be found here : 

https://spring.io/guides#gs/

MySQL config guide can be found here : 

https://spring.io/guides/gs/accessing-data-mysql/


## Goals 

- Create a full CRUD application for a company called NextStep

- NextStep is a company that wants to provide end-to-end-management of their service delivery

- Company website 

com.arunabhdas.nextstep

## Environment

- (LAMJA) - Linux-ApacheTomcat-MySQL-Java

## Dev Environment

- VSCode with Java support extensions

## System Checks

- Check if java is installed 

==> java -version
java version "1.8.0_231"
Java(TM) SE Runtime Environment (build 1.8.0_231-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.231-b11, mixed mode)

- Check if maven is installed

==> mvn -v
Apache Maven 3.6.2 (40f52333136460af0dc0d7232c0dc0bcf0d9e117; 2019-08-27T11:06:16-04:00)
Maven home: /usr/local/Cellar/maven/3.6.2/libexec
Java version: 1.8.0_231, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk1.8.0_231.jdk/Contents/Home/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.15", arch: "x86_64", family: "mac"


## Steps

- Bootstrap SpringBoot application using VSCode

- Update application.properties as follows :

~~~
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/db_example?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.username=springuser
spring.datasource.password=ThePassword
debug=true
~~~


## Run in dev



==> mvn spring-boot:run



# Links 

https://medium.com/better-programming/building-a-spring-boot-rest-api-a-php-developers-view-part-i-6add2e794646


https://www.youtube.com/watch?v=KiYIQYkyr8Y

https://www.youtube.com/watch?v=4-XE3YljDMk

