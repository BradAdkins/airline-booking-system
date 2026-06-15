# Step 1: Use Java 17+ base image
FROM openjdk:17-slim

# Step 2: Set working directory in the container
WORKDIR /app

# Step 3: Copy the compiled jar and any files needed
COPY target/BookingSystem-1.0.jar app.jar
COPY randomnames.csv randomnames.csv

# Step 4: Expose port 8000
EXPOSE 8000

# Step 5: Run the app
CMD ["java", "-jar", "app.jar"]
