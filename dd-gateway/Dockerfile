FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY /var/lib/docker/tmp/dd-gateway/dd-gateway.jar dd-gateway-1.0.jar
ENTRYPOINT ["java","-jar","/dd-gateway.jar", "&"]