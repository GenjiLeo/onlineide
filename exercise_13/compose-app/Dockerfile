# Extends From an existing image
FROM openjdk:8-jdk-alpine
# Set working directory
WORKDIR /app
# Copy .jar in container
COPY target/*.jar app.jar
#Expose port
EXPOSE 8080

# Or entrypoint
# Continuosly runs until stopped since it's a WS
# Would otherwise tear down the container after executing
CMD java -jar /app/app.jar


# How to build
# Run Docker daemon
# mvn package -DskipTests
# docker build --tag time-tracking-app:1.0 .

# see images: docker images 
# | grep time-tracking

# Create and run port to port 80
# docker run --pubish 80:8080 appname
# Write logs to local
# docker run --publish 80:8080 --volume ./logs:logs  appname

# Send docker image
# docker login <user> <pwd> <registry_location>
# store image in registry, then pull
# docker push <img>
# docker pull <img> in another machine
#