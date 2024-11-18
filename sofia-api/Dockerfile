# Etapa de construção
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa de execução
FROM openjdk:17-jdk-slim

ENV APP_DIR=/usr/app/

RUN mkdir -p $APP_DIR

WORKDIR $APP_DIR

COPY --from=build /app/target/com.sofia.backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]