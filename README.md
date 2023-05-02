## _Java 8 + SPRING BOOT RESTful API + MySQL_


### Tech Requirements

- [Java 8 jdk](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html) - Java SE Development Kit.
- Any Java IDE: Eclipse, NetBeans, [IntelliJ IDEA*](https://www.jetbrains.com/idea/download).
- [MySQL database](https://dev.mysql.com/downloads/) - MySQL community.

### Installation

- Create a new blank schema with the name "lunih" on MySQL.
```sql
CREATE SCHEMA `lunih` ;
```

- Clone this repository.
- Resolve any missing dependencies.
- Change connection authentication with your confidential on src/main/resources/application.properties.
```java
spring.datasource.username=<username>
spring.datasource.password=<password>
```

- The default port of the project is 8080, to change it simply modify the port number.
```java
server.port=8080
```
- In case you're using IntelliJ IDEA community edition, edit your Run/Debug [configuration](https://stackoverflow.com/a/72956557).
- Run/Debug the project.
- To access SwaggerUI, open your browser and go to: http://localhost:8080/

### Useful Plugins
To be updated.

### Development

- Create your own branch and start coding.
- Make changes in your file, commit, push on your branch.
- Create a merge request to master.

### License
To be updated.
