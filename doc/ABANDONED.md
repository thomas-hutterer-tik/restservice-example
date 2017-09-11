### ABANDONED Play Framework
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

## ABANDONED Persistence Options
#Play EBean:
* Sample Play V2.6: 
* build.sbt: jdbc, PlayJava, PlayEbean
* Bean: 
* Repository: -+ a bit complex due to async (ExecCtx + 1 class per eban)
* Controller: 
* DB: in memory h2

#Play JPA
- JPA with support for async DB access
* Sample Play V2.6: play-java-jpa-example
* build.sbt: javaJpa
* Bean: ++ only public field; getter, setter injected
* Repository: * complex 3 classes needed
* Controller: * complex because of async
* DB: in memory

# Spring, Hibernate => PREFERRED without Play
* Sample: play-spring-data-jpa => RUNTIME FAILURE
==> play-java-spring-data-jpa
* build.sbt: javaJpa, spring-context, spring-data-jpa, 
* Bean: ++ simple 6 lines
* Repository: ++ simple 4 lines
* Controller: ++ simple
* DB: 

Java 8: CompletionStage used from chaining of dependent async actions
