FROM openjdk:11.0.4-jre-slim-buster
ARG JAR_FILE=target/mini-autorizador-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
