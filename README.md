# Spring only

## REST Framework

### Spring boot Template: 
* create with http://start.spring.io/, add dependencies: REST repositories, JPA
* use template gs-rest-service from: https://github.com/spring-guides/gs-rest-service/tree/master/complete
* create THT template on GitHub
	* [TODO] create related Entity Communication

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
* [TODO] live editing / hot reloading of changes 

### [TODO] Service Registry: Consul

### [TODO] REST API Documentation: Swagger
* look at: https://jaxenter.de/spring-boot-tutorial-54020

## Persistence

### Persistence Framework: JPA (Part of Java 8, wide tool support)
* add spring dependency in pom.xml
* Auto generate getters and setters: import lombok.Data
	* from: http://www.vogella.com/tutorials/JavaPersistenceAPI/article.html

### OR-Mapper: Hibernate plus Spring JPA

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

### Security

* [TODO] Authentication: OAuth 2.0, JWT
* Disable SSL for DB-Access:
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

![SonarResult](/gs-rest-service/doc/SonarResult-2017-09-03.png)

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
