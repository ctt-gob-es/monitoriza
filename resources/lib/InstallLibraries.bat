@echo off
call mvn install:install-file -Dfile=./es/gob/logconsumer/log-consumer-api/1.1/log-consumer-api-1.1.jar -DgroupId=es.gob.logconsumer -DartifactId=log-consumer-api -Dversion=1.1 -Dpackaging=jar -DpomFile=./es/gob/logconsumer/log-consumer-api/1.1/log-consumer-api-1.1.pom
call mvn install:install-file -Dfile=./es/gob/logconsumer/log-consumer-register/1.1/log-consumer-register-1.1.jar -DgroupId=es.gob.logconsumer -DartifactId=log-consumer-register -Dversion=1.1 -Dpackaging=jar -DpomFile=./es/gob/logconsumer/log-consumer-register/1.1/log-consumer-register-1.1.pom