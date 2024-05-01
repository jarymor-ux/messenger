FROM eclipse-temurin:latest
ARG JAR_FILE=target/*.jar
COPY ./target/discovery-service-0.0.1-SNAPSHOT.jar discovery.jar
LABEL authors="jarym"

ENTRYPOINT ["java", "-jar", "/discovery.jar"]