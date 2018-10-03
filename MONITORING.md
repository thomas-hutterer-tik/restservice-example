# Monitoring Sample

## App under observation
* REST endpoint: http://localhost:8080/users
* Prometheus endpoint: http://localhost:8080/prometheus

### how to activate Prometheus metrics in a spring boot application

* add the actuator and io.micrometer dependencies in pom.xml
``` xml
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>
	<dependency>
	    <groupId>io.micrometer</groupId>
	    <artifactId>micrometer-spring-legacy</artifactId>
	    <version>1.0.6</version>
	</dependency>
	<dependency>
	    <groupId>io.micrometer</groupId>
	    <artifactId>micrometer-registry-prometheus</artifactId>
	    <version>1.0.6</version>
	</dependency>
```


## Windows node exporter for Prometheus
* http://localhost:9182/metrics

## Prometheus, monitoring solution, TimeSeries-DB
* Prometheus endpoint of prometheus (eat you won dog food): http://localhost:9090/metrics 
* Promethues UI (not working on windows in current version 4.0): http://localhost:9090/graph

## Grafana: UI for creating beautiful monitoring dash boards from many data sources including Prometheus 
* http://localhost:3000/d/TAMKz9oiz/spring-boot-statistics?refresh=10s&orgId=1

## installation & startup

I install the following components in one directory: 
* Prometheus
* Grafana
* host metrics
	* wmi_exporter for windows or
	* node_exporter fro unix

```bash
#!/usr/bin/env sh
./prometheus-2.4.2.windows-amd64/prometheus.exe --config.file=./prometheus.yml &
echo 'prometheus started'
./grafana/bin/grafana-server.exe --homepath=./grafana &
echo 'grafan started'
wmi_exporter/wmi_exporter.exe --collector.textfile.directory=./wmi_exporter & 
echo 'wmi_expoerter started as a service'
```

## Build docker image
Eclipse -> Run Maven ...: target: install dockerfile:build
docker images

## deploy image to CRP:

### fix CRP ssh issue
docker-machine ssh default
Open Docker profile
sudo vi /var/lib/boot2docker/profile
Add this line to the bottom of the profile file. If EXTRA_ARGS already exists, add the insecure registry flag to the EXTRA_ARGS. Substitute in the path[s] to your registries.
EXTRA_ARGS=" --insecure-registry docker-registry.apps.tools.adp.allianz"
Save the profile changes and 'exit' out of the docker-machine bash back to your machine. Then Restart Docker VM substituting in your docker-machine name
docker-machine restart default

### do the deploy
sudo vi ...: allow unsecure connect to CRP
docker-machine restart default
docker login -u thomas.hutterer-tik@allianz.at -p <token> docker-registry.apps.tools.adp.allianz
docker tag 06b7604068e2 docker-registry.apps.tools.adp.allianz/insurance-automation/restservice-example
docker push docker-registry.apps.tools.adp.allianz/insurance-automation/restservice-example:latest
