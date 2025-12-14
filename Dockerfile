

# Stage 1: Build the application
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
# Copy project files (pom.xml, src, etc.)
COPY . .
RUN chmod +x mvnw
# Use Maven wrapper to clean and package the application into a JAR
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final, smaller runtime image
FROM eclipse-temurin:21-jre-alpine
# Expose the default Spring Boot port
EXPOSE 8080 
# Copy only the JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]