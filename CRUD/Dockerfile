FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/CRUD-0.0.1-SNAPSHOT.jar /app/CRUD-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/CRUD-0.0.1-SNAPSHOT.jar"]
