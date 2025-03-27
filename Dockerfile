FROM ubuntu:latest AS BUILD


RUN "apt-get update"
RUN "apt-get install openjdk-17-se"
