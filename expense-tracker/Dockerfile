# Use an official OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the WAR file into the container
COPY target/expense-tracker-0.0.1-SNAPSHOT.war app.war

# Expose the port the app will run on
EXPOSE 8080

# Run the application using java -jar
CMD ["java", "-jar", "app.war"]
