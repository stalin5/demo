# ---- Build stage ----
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
# Build without tests for faster deploys; remove -DskipTests if needed
RUN mvn -B -q clean package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the built jar from the build stage; rename to app.jar
COPY --from=build /app/target/*.jar /app/app.jar

# Render will map its own PORT env; Spring Boot will read it if server.port is set to ${PORT:8080}
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+UseContainerSupport"
ENV PORT=8080

EXPOSE 8080

# Use exec form so signals are properly forwarded on Render
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar --server.port=${PORT}"]
