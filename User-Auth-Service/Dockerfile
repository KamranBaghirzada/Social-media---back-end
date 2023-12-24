FROM openjdk:17-jdk-slim
EXPOSE 8080
ADD target/spring-user-auth.jar spring-user-auth.jar
ENTRYPOINT ["java","-jar","spring-user-auth.jar"]