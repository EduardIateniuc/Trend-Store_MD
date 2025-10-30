# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app
# Copy the application jar file to the container
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
# Run the application
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
