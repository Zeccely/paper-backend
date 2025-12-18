FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the jar from the build stage
COPY /build/libs/*.jar app.jar

# Run the application
CMD ["java", "-jar", "app.jar"]
# builds image


#docker build -t zeccely/i_hate_paper-backend:latest . --no-cache
#docker push zeccely/i_hate_paper-backend:latest
#docker run -p 8080:8080 zeccely/i_hate_paper-backend:latest