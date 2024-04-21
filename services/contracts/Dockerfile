FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} contracts.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /contracts.jar ${0} ${@}"]
