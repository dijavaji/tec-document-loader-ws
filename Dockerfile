#define base docker image
FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY target/*.jar /app/application.jar

EXPOSE 8082

LABEL maintainer="technoloqie.com.ec"

ENTRYPOINT ["java", "-jar", "application.jar"]
