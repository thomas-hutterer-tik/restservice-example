#!/usr/bin/env sh
./prometheus-2.4.2.windows-amd64/prometheus.exe --config.file=./prometheus.yml &
echo 'prometheus started'
./grafana/bin/grafana-server.exe --homepath=./grafana &
echo 'grafan started'
wmi_exporter/wmi_exporter.exe --collector.textfile.directory=./wmi_exporter & 
echo 'wmi_expoerter started as a service'

