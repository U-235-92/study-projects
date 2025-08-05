#FASHION #3
FROM maven:3-eclipse-temurin-23-alpine
EXPOSE 8558
WORKDIR /app
COPY . .
RUN ["mvn", "clean", "install"]
CMD ["mvn", "spring-boot:run"]

#FASHION #2
#FROM eclipse-temurin:23-alpine as build
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#RUN ["mvn", "clean", "install", "-DskipTests=true"]
#
#FROM eclipse-temurin:23-alpine
#EXPOSE 8558
#WORKDIR /app
#COPY --from=build /app/target/*.jar user-service-1.0.0.jar
#CMD [ "java", "-jar", "user-service-1.0.0.jar"]

#FASHION #1
#FROM eclipse-temurin:23-alpine
#EXPOSE 8558
#WORKDIR /app
#COPY ./target/user-service-1.0.0.jar ./user-service-1.0.0.jar
#CMD ["java", "-jar", "/app/user-service-1.0.0.jar"]