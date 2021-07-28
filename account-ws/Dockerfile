FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/account-ws-0.0.1-SNAPSHOT.jar account-ws.jar
ENTRYPOINT ["java","-jar","account-ws.jar"]