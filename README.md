# Spring only

![SonarResult](https://github.com/thomas-hutterer-tik/restservice-example/blob/master/doc/SonarResult-2017-09-03.png)

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
	* -Dorg.apache.logging.log4j.simplelog.StatusLogger.level=trace
	* -Dlog4j.configurationFile=log4j2-test.properties
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

## Build

### Development tools

* Source Repository: GitHub

* Maven:
	* installed in: /usr/local/Cellar/maven/3.3.9/bin
	* links added to $PATH: /usr/local/bin

* Build automation: Jenkins
	* Installation for Mac OsX from official site
	* http://localhost:8080
	* Admin user thomas created
	* Global Setting: Allow anonymous read
	* Project-Setting: Enable Poll SCM
	* Add to project file: ".git/hooks/post-commit" with this content adapted to the project
		```bash
		!/bin/sh
		curl http://localhost:8080/git/notifyCommit?url=file:///Users/thomas/Source/git/restservice-example
		```
	* add "SonarQube Scanner for Jenkins" plugin


* Sonar Qube:
  http://localhost:9000/dashboard?id=org.springframework%3Ags-rest-service
	*  Installed in: /usr/local/sonarqube-6.5/bin/macosx-universal-64/
	*  start with:
		`` `bash
		/usr/local/sonarqube-6.5/bin/macosx-universal-64/sonar.sh start
		` ``
	*  build project with: mvn clean install sonar:sonar
	*  Access results: http://localhost:9000/projects?sort=-analysis_date
	*  Default User: admin/admin
	*  change DB to MySQL
		*  created schema "sonar"
		*  create user "sonar", "sonar_db"
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

# Cloud Foundry

Download CF tools
* cf login -a https://api.sys.adp.allianz -u dragan.gaic@allianz.at -p <YOUR PASSWORD GOES HERE>
	* Choose a provider organization, in our case it's: IT Summit Coding Session
	* Chose a user, pick any available, in my case its Developer
	* You're now logged in
* Now you need to push an application.
	* In your current command line open a SpringBoot project folder. I know it works with Maven projects.
	* Execute: cf push <APP_NAME>
	* This will push your application to CloudFoundry


# MySQL

- get and run docker image:

  `docker run --name mysql-itsummit -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql/mysql-server:latest`

- create db and user:

  `docker exec -it 30fc0345984d mysql -uroot -p`
```
CREATE DATABASE foo;
CREATE USER 'foo'@'%' IDENTIFIED BY 'bar';
GRANT ALL PRIVILEGES ON foo . * TO 'foo';
FLUSH PRIVILEGES;
```

- stop/start container:

	`docker ps --filter "status=exited" | grep mysql`

  `docker stop/start 30fc0345984d`
