FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN chmod +x mvnw

COPY src src

RUN ./mvnw -B clean package -DskipTests

FROM gcr.io/distroless/java21-debian12:nonroot

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
USER nonroot

ENTRYPOINT ["java", "-jar", "app.jar"]