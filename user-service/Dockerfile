FROM eclipse-temurin:latest
ARG JAR_FILE=target/*.jar
COPY ./target/user-service-0.0.1-SNAPSHOT.jar user-service.jar
LABEL authors="jarym"

ENTRYPOINT ["java", "-jar", "/user-service.jar"]