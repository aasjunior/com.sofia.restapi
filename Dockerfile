FROM openjdk:17-jdk-alpine

ENV APP_DIR=/usr/app/

RUN mkdir -p $APP_DIR

WORKDIR $APP_DIR

COPY ./sofia-api/target/com.sofia.backend-0.0.1-SNAPSHOT.jar $APP_DIR

EXPOSE 8080

CMD ["java", "-jar", "com.sofia.backend-0.0.1-SNAPSHOT.jar"]
