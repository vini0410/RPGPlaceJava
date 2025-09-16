# Stage 1: Build the application using Maven and Java 24
FROM maven:3.9.6-eclipse-temurin-24 AS build
WORKDIR /app

# Copy pom.xml and download dependencies first to leverage Docker cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final, smaller image with Java 24 JRE
FROM eclipse-temurin:24-jre
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/RPGPlace-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
