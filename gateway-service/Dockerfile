FROM eclipse-temurin:latest
ARG JAR_FILE=target/*.jar
COPY ./target/gateway-service-0.0.1-SNAPSHOT.jar gateway.jar
LABEL authors="jarym"

ENTRYPOINT ["java", "-jar", "/gateway.jar"]