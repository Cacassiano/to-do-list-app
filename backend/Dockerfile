FROM maven:4.0.0-rc-4-amazoncorretto-17-al2023 AS build
WORKDIR /build

COPY . .
RUN mvn clean install -DskipTests


FROM activestate/jre
WORKDIR /app

COPY --from=build build/target/to-do-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]



