FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/discovery-ws-0.0.1-SNAPSHOT.jar discovery-ws.jar
ENTRYPOINT ["java","-jar","discovery-ws.jar"]