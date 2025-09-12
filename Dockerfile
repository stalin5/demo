
     FROM maven:3.9.3-jdk-17 AS build

     WORKDIR /app


WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Package the application without running tests
RUN mvn clean package -DskipTests

# Use a smaller JRE image to run the app
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
