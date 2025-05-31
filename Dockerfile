# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-slim

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/aibouauth-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8882

# Run the application
CMD ["java", "-jar", "app.jar"]
