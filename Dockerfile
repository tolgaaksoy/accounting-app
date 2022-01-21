FROM amazoncorretto:11-alpine-jdk

MAINTAINER dev.tolgaaksoy@gmail.com

COPY ./target/accounting-app-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch accounting-app-0.0.1-SNAPSHOT.jar'

ARG JAR_FILE=target/accounting-app-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","accounting-app-0.0.1-SNAPSHOT.jar"]
