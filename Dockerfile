FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/smartshopper-0.0.1.jar smartshopper-0.0.1.jar
ENTRYPOINT ["java","-jar","/smartshopper-0.0.1.jar"]