FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} controller.jar
ENTRYPOINT ["tail", "-f", "/dev/null"]