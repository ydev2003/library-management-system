# Step 1: Build Stage
FROM maven:3.8.5-openjdk-11 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and the source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Step 2: Runtime Stage
FROM openjdk:11-jre-slim

# Set the working directory in the runtime container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/Library-Management-System-Backend-0.0.1-SNAPSHOT.jar app.jar

# Set environment variable for MongoDB connection
ENV SPRING_DATA_MONGODB_URI=mongodb+srv://lms:lms@cluster0.m99hx.mongodb.net/Library-Management-System

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]