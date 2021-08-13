FROM adoptopenjdk/openjdk11

RUN adduser --system --group spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} current-account-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/current-account-service-0.0.1-SNAPSHOT.jar"]