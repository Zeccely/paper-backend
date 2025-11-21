# ------------------------
# 1. Build stage
# ------------------------
FROM gradle:8.7-jdk21 AS build
WORKDIR /app

# Copy everything and build
COPY . .
RUN gradle build -x test --no-daemon

# ------------------------
# 2. Run stage
# ------------------------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Run the application
CMD ["java", "-jar", "app.jar"]
# builds image
#docker build -t paperbackend .

#
#docker run -p 8080:8080 paperbackend