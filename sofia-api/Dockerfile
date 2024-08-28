FROM openjdk:17-jdk-alpine
LABEL authors="aasjunior"

# Define variáveis de ambiente com base em variáveis já configuradas no sistema
ENV MONGO_ATLAS_URL=${MONGO_ATLAS_URL}
ENV JWT_SECRET=${JWT_SECRET}

COPY target/com.sofia.backend-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]