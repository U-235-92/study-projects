FROM maven:3-eclipse-temurin-21-alpine AS builder
WORKDIR /build
COPY ./src ./src
COPY ./pom.xml ./pom.xml
RUN [ "mvn", "clean", "package", "-DskipTests" ]

FROM eclipse-temurin:21-jre-alpine
EXPOSE 5085
WORKDIR /app
COPY --from=builder /build/target/*.jar ./application.jar
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql-db-service:3306/aq_resource_service_db?createDatabaseIfNotExist=true
CMD [ "java", "-jar", "application.jar" ]