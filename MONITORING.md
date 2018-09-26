# Monitoring Sample

## App under observation
* REST endpoint: http://localhost:8080/users
* Prometheus endpoint: http://localhost:8080/prometheus

## Windows node exporter for Prometheus
* http://localhost:9182/metrics

## Prometheus, monitoring solution, TimeSeries-DB
* Prometheus endpoint of prometheus (eat you won dog food): http://localhost:9090/metrics 
* Promethues UI (not working on windows in current version 4.0): http://localhost:9090/graph

## Grafana: UI for creating beautiful monitoring dash boards from many data sources including Prometheus 
* http://localhost:3000/d/TAMKz9oiz/spring-boot-statistics?refresh=10s&orgId=1

## installation & startup

```bash
#!/usr/bin/env sh
/c/Program\ Files/prometheus/prometheus.exe --config.file=/c/Program\ Files/prometheus/prometheus.yml &
echo 'prometheus started'
/c/Program\ Files/grafana/bin/grafana-server.exe --homepath=/c/Program\ Files/grafana &
echo 'grafan started'
echo 'wmi_expoerter started as a service'
```
