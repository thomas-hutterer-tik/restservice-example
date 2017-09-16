# Spring only

![SonarResult](/restservice-example/doc/SonarResult-2017-09-03.png)

## Create a new project step by step

on GitHub:
* Create new repository on GitHub with README.md
* Copy .gitignore and LICENSE from https://github.com/thomas-hutterer-tik/restservice-example
* Commit all 3 files

Open a new Terminal window
```bash
$ cd Source/Spring
$ git clone https://github.com/we-save-energy/wse-pi
``` 

in Eclipse:
* Menu: File->Open Project From Files system ...
	* Select <local project directory>
	* .project file is created by Eclipse

## Development tools

What need to run on the development server:
```bash
$ /usr/local/Cellar/mosquitto/1.4.14/mosquitto -c /usr/local/Cellar/mosquitto/1.4.14/mosquitto.conf
$ golang/bin/prometheus -config.file=/usr/local/prometheus/prometheus.yml
```

### Prometheus
http://localhost:9090/graph?g0.range_input=1h&g0.expr=smartpi_active_watts&g0.tab=0

## REST Framework

### Spring boot Template: 
* create with http://start.spring.io/, add dependencies: REST repositories, JPA
* use template gs-rest-service from: https://github.com/spring-guides/gs-rest-service/tree/master/complete
* create THT template on GitHub
	* [TODO] create related Entity Communication
* to add REST support to any project add
		```xml
	   <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
        </dependency>
		```
	

### Testing:
* UnitTest
* IntegrationTest use: org.springframework.test.web.servlet.MockMvc
* REST TestClient use: org.springframework.web.client.RestTemplate

### IDE: STS - Spring Tool Suite, based on Eclipse 4.7
* download from: https://spring.io/tools/sts/all
* Stop DEBUG logging in test
	* from: https://www.mkyong.com/spring-boot/spring-boot-test-how-to-stop-debug-logs/
* Add Markdown Editor for README.md:
	* http://www.nodeclipse.org/updates/markdown/
* live editing / hot reloading of changes: 
	* add dependency to pom.xml: 
	```xml    
	<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
    ```
	

### [TODO] Service Registry: Consul

### [TODO] REST API Documentation: Swagger
* look at: https://jaxenter.de/spring-boot-tutorial-54020

## Persistence

### Framework + OR-Mapper: ## JPA (Part of Java 8, wide tool support, includes Hibernate

add spring dependency in pom.xml:
		```xml
        <dependency>
       	 	<groupId>org.springframework.boot</groupId>
        		<artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
        		<groupId>javax.inject</groupId>
        		<artifactId>javax.inject</artifactId>
        		<version>1</version>
        </dependency>
		```
Auto generate getters and setters: import lombok.Data
	* from: http://www.vogella.com/tutorials/JavaPersistenceAPI/article.html
		```xml
        <dependency>
        		<groupId>org.projectlombok</groupId>
        		<artifactId>lombok</artifactId>
        </dependency>
		```

### Database: MySQL
* installed in: /usr/local/mysql
* Autostart Config in Systemeinstellungen => MySQL
* MySQL Workbench from: https://dev.mysql.com/downloads/workbench/
* connect template to MySQL/foo
	* add to application.properties:
		* spring.datasource.url=jdbc:mysql://localhost/foo
		* spring.datasource.username=foo
		* spring.datasource.password=bar
		* spring.datasource.driver-class-name=com.mysql.jdbc.Driver
		* spring.jpa.hibernate.ddl-auto=create
	* add dependency to pom.xml: mysql, mysql-connector-java
		```xml
       <dependency>
        		<groupId>mysql</groupId>
        		<artifactId>mysql-connector-java</artifactId>
        </dependency>
		```

### Security

* [TODO] Authentication: OAuth 2.0, JWT

* Database
	* create individual user for each application (eg wse_db for wse applications)
	* grant access to user on to application schema (eg schema energy)
	* [TODO] Encrypt DB pwd in application.properties
	* [TODO] Enable SSL for DB-Access: (currently disbabled with:)
		* spring.datasource.url=jdbc:mysql://localhost/foo?verifyServerCertificate=false&useSSL=false&requireSSL=false

### Build

* Source Repository: GitHub

* Maven:
	* installed in: /usr/local/Cellar/maven/3.3.9/bin
	* links added to $PATH: /usr/local/bin

* [TODO] Build automation: Jenkins

* Sonar Qube: http://localhost:9000/dashboard?id=org.springframework%3Ags-rest-service
	* Installed in: /usr/local/sonarqube-6.5/bin/macosx-universal-64/
	* start with: sh sonar.sh start
	* build project with: mvn clean install sonar:sonar
	* Access resutls: http://localhost:9000/projects?sort=-analysis_date 
	* Default User: admin/admin
	* change DB to MySQL
		* created schema "sonar"
		* create user "sonar", "sonar_db"

### Code Coverage:
include test results: use JaCoCo from ECL Emma
* install ECL Emma in Eclipse from : http://update.eclemma.org/ 
* add to file sonar.properties:
	* sonar.junit.reportPaths=target/surefire-reports
* add dependency to pom.xml: org.jacoco, jacoco-maven-plugin
* add build definition to pom.xml for: org.jaccoco
* to create reports run: mvn jacoco:report 

### [TODO] Deployment

* Binary Repo: Docker Registry
* Container: Docker
* Hosting:  AWS-EC2

### Automation

* [TODO] Automate runtime startup with shell script
* [TODO] Automate development environment setup with Docker
* [TODO] Deploy development environment to AWS
