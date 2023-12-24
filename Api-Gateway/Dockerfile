FROM openjdk:17-jdk-slim
EXPOSE 8080
ADD target/spring-api-gateway.jar spring-api-gateway.jar
ENTRYPOINT ["java","-jar","spring-api-gateway.jar"]