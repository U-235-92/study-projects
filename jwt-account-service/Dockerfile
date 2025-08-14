FROM eclipse-temurin:21-alpine-3.21
EXPOSE 5082
WORKDIR /app
COPY ./target/jwt-account-service-1.0.0.jar .
CMD [ "java", "-jar", "jwt-account-service-1.0.0.jar" ]