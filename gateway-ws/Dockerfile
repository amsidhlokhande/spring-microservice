FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/gateway-ws-0.0.1-SNAPSHOT.jar gateway-ws.jar
ENTRYPOINT ["java","-jar","gateway-ws.jar"]