# Use a base image with Gradle and OpenJDK installed
FROM gradle:7.5.1-jdk17 AS build

# Copy the entire project into the container
COPY . .

# Execute Gradle clean and build commands, skipping the tests
RUN gradle clean build -x test

# Use a slim version of OpenJDK for the runtime image
FROM openjdk:17.0.1-jdk-slim

# Copy the built jar from the build stage to the final image
COPY --from=build /home/gradle/build/libs/*.jar demo.jar

# Expose port 8080 for the application
EXPOSE 8080

# Set the container's entrypoint to run the application
ENTRYPOINT ["java", "-jar", "demo.jar"]