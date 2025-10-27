FROM ubuntu:latest AS build


RUN apt-get update
RUN apt-get install openjdk-17-jdk
COPY . .


RUN apt-get maven -y
RUN mvn clean install -DskipTests

FROM openjdk:17-slim
EXPOSE 8080

COPY --from=build target/to-do-api-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]

