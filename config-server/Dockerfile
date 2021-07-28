FROM openjdk:8-alpine3.9
VOLUME /tmp
COPY spring-cloud-app-config/dev/api-encryption-key.jks api-encryption-key.jks
COPY spring-cloud-app-config/dev/UnlimitedJCEPolicyJDK8/* /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.252.b09-2.51.amzn1.x86_64/jre/lib/security/
COPY target/config-server-0.0.1-SNAPSHOT.jar ConfigServer.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "ConfigServer.jar"]