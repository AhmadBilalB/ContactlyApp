# Stage 1: Build Stage
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the project files to the container
COPY pom.xml .
COPY src ./src

# Run Maven build
RUN mvn clean package -DskipTests

# Stage 2: Runtime Stage
FROM eclipse-temurin:17-jre

# Set the working directory
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/Contactly-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on (default: 8080)
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
