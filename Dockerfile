# Stage 1: Build the application using Maven Wrapper
FROM openjdk:21-jdk-slim as build

# Set the working directory
WORKDIR /app

# Copy the Maven wrapper and the pom.xml file
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:resolve

# Copy the source code and build the application
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the Spring Boot application
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/mercadolibre-challenge-geolocalizacion-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Set environment variables for MySQL
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql-db:3306/geolocation?useSSL=false&allowPublicKeyRetrieval=true
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=test1234

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
