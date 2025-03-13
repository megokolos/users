FROM openjdk:17-jdk-slim

WORKDIR /app

COPY CRUD/target/CRUD-0.0.1-SNAPSHOT.jar /app/CRUD-0.0.1-SNAPSHOT.jar
COPY CRUD/src/main/resources /app/resources

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/CRUD-0.0.1-SNAPSHOT.jar"]
