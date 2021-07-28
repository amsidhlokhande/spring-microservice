FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/albums-ws-0.0.1-SNAPSHOT.jar albums-ws.jar
ENTRYPOINT ["java","-jar","albums-ws.jar"]