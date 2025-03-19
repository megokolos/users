FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY CRUD /app
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/CRUD-0.0.1-SNAPSHOT.jar /app/CRUD-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/CRUD-0.0.1-SNAPSHOT.jar"]
