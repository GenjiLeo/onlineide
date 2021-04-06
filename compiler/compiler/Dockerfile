FROM openjdk:14-jdk-alpine
# Install GCC
RUN apk add build-base
WORKDIR /app
COPY target/*.jar app.jar
CMD java -jar /app/app.jar
