@echo off
call mvn install:install-file -Dfile=./clave/eidas-commons/1.4.3/eidas-commons-1.4.3.jar -DgroupId=clave -DartifactId=eidas-commons -Dversion=1.4.3 -Dpackaging=jar

call mvn install:install-file -Dfile=./clave/eidas-configmodule/1.4.3/eidas-configmodule-1.4.3.jar -DgroupId=clave -DartifactId=eidas-configmodule -Dversion=1.4.3 -Dpackaging=jar

call mvn install:install-file -Dfile=./clave/eidas-encryption/1.4.3/eidas-encryption-1.4.3.jar -DgroupId=clave -DartifactId=eidas-encryption -Dversion=1.4.3 -Dpackaging=jar

call mvn install:install-file -Dfile=./clave/eidas-light-commons/1.4.3/eidas-light-commons-1.4.3.jar -DgroupId=clave -DartifactId=eidas-light-commons -Dversion=1.4.3 -Dpackaging=jar

call mvn install:install-file -Dfile=./clave/eidas-saml-engine/1.4.3/eidas-saml-engine-1.4.3.jar -DgroupId=clave -DartifactId=eidas-saml-engine -Dversion=1.4.3 -Dpackaging=jar

call mvn install:install-file -Dfile=./clave/saml-engine/1.4.3/saml-engine-1.4.3.jar -DgroupId=clave -DartifactId=saml-engine -Dversion=1.4.3 -Dpackaging=jar



call mvn install:install-file -Dfile=./org/bouncycastle/bcprov-jdk16/1.46/bcprov-jdk16-1.46.jar -DgroupId=org.bouncycastle -DartifactId=bcprov-jdk16 -Dversion=1.46 -Dpackaging=jar

call mvn install:install-file -Dfile=./org/opensaml/openws/1.5.5/openws-1.5.5.jar -DgroupId=org.opensaml -DartifactId=openws -Dversion=1.5.5 -Dpackaging=jar
call mvn install:install-file -Dfile=./org/opensaml/opensaml/2.6.6/opensaml-2.6.6.jar -DgroupId=org.opensaml -DartifactId=opensaml -Dversion=2.6.6 -Dpackaging=jar



