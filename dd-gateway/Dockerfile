FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/dd-gateway.jar dd-gateway.jar
ENTRYPOINT ["java","-jar","/dd-gateway.jar", "&"]