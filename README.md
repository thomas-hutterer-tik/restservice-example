========================== Spring only: ============================

------------- REST Framework -------------

Spring boot Template: 
- create with http://start.spring.io/, add dependencies: REST repositories, JPA
- use template gs-rest-service from: https://github.com/spring-guides/gs-rest-service/tree/master/complete
- create THT template on GitHub
[TODO] -- create related Entity Communication

Testing:
- UnitTest
- IntegrationTest use: org.springframework.test.web.servlet.MockMvc
- REST TestClient use: org.springframework.web.client.RestTemplate

IDE: STS - Spring Tool Suite, based on Eclipse 4.7
- download from: https://spring.io/tools/sts/all
- Stop DEBUG logging in test
-- from: https://www.mkyong.com/spring-boot/spring-boot-test-how-to-stop-debug-logs/
- Add Markdown Editor for README.md:
-- http://www.nodeclipse.org/updates/markdown/

[TODO] Service Registry: Consul

[TODO] REST API Documentation: Swagger
-- look at: https://jaxenter.de/spring-boot-tutorial-54020

------------- Persistence -------------

Persistence Framework: JPA (Part of Java 8, wide tool support)
- add spring dependency in pom.xml
- Auto generate getters and setters: import lombok.Data
-- from: http://www.vogella.com/tutorials/JavaPersistenceAPI/article.html

[TODO] OR-Mapper: Hibernate or Spring ???

Database: MySQL
- installed in: /usr/local/mysql
- Autostart Config in Systemeinstellungen => MySQL
- MySQL Workbench from: https://dev.mysql.com/downloads/workbench/
- connect template to MySQL/foo
-- add to application.properties:
---- spring.datasource.url=jdbc:mysql://localhost/foo
---- spring.datasource.username=foo
---- spring.datasource.password=bar
---- spring.datasource.driver-class-name=com.mysql.jdbc.Driver
---- spring.jpa.hibernate.ddl-auto=create
-- add dependency to pom.xml: mysql, mysql-connector-java

------------- Security -------------

[TODO] Authentication: OAuth 2.0, JWT
Disable SSL for DB-Access:
- spring.datasource.url=jdbc:mysql://localhost/foo?verifyServerCertificate=false&useSSL=false&requireSSL=false

------------- Build -------------

Source Repository: GitHub

Maven:
- installed in: /usr/local/Cellar/maven/3.3.9/bin
- links added to $PATH: /usr/local/bin

[TODO] Build automation: Jenkins

Sonar Qube: http://localhost:9000/dashboard?id=org.springframework%3Ags-rest-service
- Installed in: /usr/local/sonarqube-6.5/bin/macosx-universal-64/
- start with: sh sonar.sh start
- build project with: mvn clean install
- run with: mvn sonar:sonar
- Access resutls: http://localhost:9000/projects?sort=-analysis_date 
- change DB to MySQL
-- created schema "sonar"
-- create user "sonar", "sonar_db"



Code Coverage:
- include test results: use JaCoCo from ECL Emma
-- install ECL Emma in Eclipse from : http://update.eclemma.org/ 
-- add to file sonar.properties:
--- sonar.junit.reportPaths=target/surefire-reports
-- add dependency to pom.xml: org.jacoco, jacoco-maven-plugin
-- to create reports run: mvn jacoco:report 

------------- Deployment -------------

Binary Repo: Docker Registry
Container: Docker
Hosting:  AWS-EC2

------------- Automation --------------

[TODO] Automate runtime startup with shell script
[TODO] Automate development environment setup with Docker
[TODO] Deploy development environment to AWS


============== ABANDONED Play Framework ======================
Use Play Version 2.6 (latest Aug 2017)
activator new play-java-jpa-example

activator compile

Add to project/plugins.sbt:
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.1.0")

Add to build.sbt:
// Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
EclipseKeys.preTasks := Seq(compile in Compile, compile in Test)
EclipseKeys.projectFlavor := EclipseProjectFlavor.Java           // Java project. Don't expect Scala IDE
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources)  // Use .class files instead of generated .scala files for views and routes

activator "eclipse with-source=true"

Eclipse -> File -> Import -> Existing Project

activator -jvm-debug 9999 "~run"

Eclipse -> Debug As -> Debug Configuration -> New -> Port 9999

================ ABANDONED Persistence Options ===================
Play EBean:
-- Sample Play V2.6: 
-- build.sbt: jdbc, PlayJava, PlayEbean
-- Bean: 
-- Repository: -+ a bit complex due to async (ExecCtx + 1 class per eban)
-- Controller: 
-- DB: in memory h2

Play JPA
- JPA with support for async DB access
-- Sample Play V2.6: play-java-jpa-example
-- build.sbt: javaJpa
-- Bean: ++ only public field; getter, setter injected
-- Repository: -- complex 3 classes needed
-- Controller: -- complex because of async
-- DB: in memory

- Spring, Hibernate => PREFERRED without Play
-- Sample: play-spring-data-jpa => RUNTIME FAILURE
==> play-java-spring-data-jpa
-- build.sbt: javaJpa, spring-context, spring-data-jpa, 
-- Bean: ++ simple 6 lines
-- Repository: ++ simple 4 lines
-- Controller: ++ simple
-- DB: 

Java 8: CompletionStage used from chaining of dependent async actions



