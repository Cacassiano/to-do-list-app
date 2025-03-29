FROM ubuntu:latest AS build


RUN "apt-get update"
RUN "apt-get install openjdk-17-jdk"

RUN "apt-get maven"
RUN "mvn clean install"


