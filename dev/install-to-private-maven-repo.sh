#!/bin/bash
# Muss im root-Verzeichnis des Projekts ausgeführt werden

LIB=spring-boot-starter-admin-client-1.0.0.RC1
REPO=/home/thomas/Entwicklung/git/maven-repo/

# Main Sources
mvn install:install-file -DlocalRepositoryPath=$REPO -DcreateChecksum=true -Dfile=target/$LIB.jar -DpomFile=pom.xml -Dsources=target/$LIB-sources.jar -Djavadoc=target/$LIB-javadoc.jar
