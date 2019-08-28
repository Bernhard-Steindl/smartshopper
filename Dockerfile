FROM dockerfile/java:oracle-java8
VOLUME /tmp
ADD target/core-0.1.0-RELEASE.jar target/smartshopper-0.0.1.jar
RUN bash -c 'touch target/smartshopper-0.0.1.jar'
ENTRYPOINT ["java","-jar","target/smartshopper-0.0.1.jar"]