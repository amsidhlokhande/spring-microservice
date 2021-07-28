FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/user-ws-0.0.1-SNAPSHOT.jar users-ws.jar
ENTRYPOINT ["java","-jar","users-ws.jar"]