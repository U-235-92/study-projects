#Multi-stage build
FROM maven:3-eclipse-temurin-21-alpine AS builder
WORKDIR /build
COPY ./src ./src
COPY ./pom.xml ./pom.xml
RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:21-alpine-3.21
EXPOSE 5082
ENV SPRING_DATASOURCE_URL=jdbc:mysql://jwt-account-mysql-db-service:3306/jwt_account_service_db?createDatabaseIfNotExist=true
WORKDIR /app
COPY --from=builder /build/target/*.jar ./application.jar
CMD [ "java", "-jar", "application.jar" ]

#One-stage build
#FROM eclipse-temurin:21-alpine-3.21
#EXPOSE 5082
#WORKDIR /app
#COPY ./target/jwt-account-service-1.0.0.jar .
#CMD [ "java", "-jar", "jwt-account-service-1.0.0.jar" ]