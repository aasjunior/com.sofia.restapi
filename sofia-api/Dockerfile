FROM openjdk:17-jdk-slim

ENV APP_DIR=/usr/app/
ENV MONGO_ATLAS_URL=mongodb://mongodb:27017/sofia_db
ENV JWT_SECRET=${JWT_SECRET}

RUN mkdir -p $APP_DIR

WORKDIR $APP_DIR

COPY target/com.sofia.backend-0.0.1-SNAPSHOT.jar $APP_DIR/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/usr/app/app.jar"]
