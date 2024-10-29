# Stage 1: Build the application using Maven Wrapper
FROM openjdk:21-jdk-slim AS build

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

RUN chmod +x mvnw
RUN ./mvnw dependency:resolve --no-transfer-progress

COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the Spring Boot application
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/mercadolibre-challenge-geolocalizacion-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
