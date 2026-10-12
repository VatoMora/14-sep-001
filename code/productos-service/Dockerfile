# Etapa 1: Compilación con Maven
FROM docker.io/library/maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml checkstyle.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera de ejecución
FROM docker.io/library/eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/productos-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]