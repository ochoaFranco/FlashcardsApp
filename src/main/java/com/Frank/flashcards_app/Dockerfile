# Stage 1: Build the application
FROM openjdk:17-jdk AS build

# Set working directory
WORKDIR /app

# Copy necessary files to the Docker container
COPY pom.xml .
COPY src src

# Copy Maven wrapper to run Maven commands
COPY mvnw .
COPY .mvn .mvn

# Give executable permission to the Maven wrapper
RUN chmod +x ./mvnw

# Package the application, skipping tests to speed up the process
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final Docker image
FROM openjdk:17-jdk

# Set a volume pointing to /tmp (optional, for temporary files)
VOLUME /tmp

# Copy the packaged JAR file from the build stage to the final image
COPY --from=build /app/target/flashcards-app-0.0.1-SNAPSHOT.jar /flashcards-app-0.0.1-SNAPSHOT.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/flashcards-app-0.0.1-SNAPSHOT.jar"]

# Expose the port the application will run on
EXPOSE 8080
