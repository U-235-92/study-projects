FROM eclipse-temurin:21-alpine
EXPOSE 8558
WORKDIR /app/user_service
COPY ./target/user-service-1.0.0.jar ./user-service-1.0.0.jar
CMD ["java", "-jar", "/app/user_service/user-service-1.0.0.jar"]