FROM maven:3-eclipse-temurin-21-alpine AS builder
WORKDIR /app/build/
COPY ./src/main ./src/main
COPY ./pom.xml ./pom.xml
RUN [ "mvn", "clean", "package", "-DskipTests=true" ]

FROM eclipse-temurin:21-jre-alpine-3.21
EXPOSE 5082
WORKDIR /app
COPY --from=builder /app/build/target/*.jar ./aq-authorization-service.jar
COPY ./aq-auth-service.jks ./aq-auth-service.jks
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql-db-service:3306/aq_authorization_service_db?createDatabaseIfNotExist=true
CMD [ "java", "-jar", "aq-authorization-service.jar", "--spring.profiles.active=prod" ]