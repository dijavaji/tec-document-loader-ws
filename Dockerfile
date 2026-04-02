# Etapa de construccion
FROM maven:3.9-amazoncorretto-17-alpine AS build

WORKDIR /app

# Copiar el archivo pom.xml y el directorio src para la construccion
COPY pom.xml .
COPY src ./src

# Construir la aplicacion
RUN mvn clean package -DskipTests

# Etapa de ejecucion
#define base docker image
FROM amazoncorretto:21-alpine-jdk

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el JAR construido desde la etapa de construccion
COPY --from=build /app/target/*.jar /app/application.jar
#COPY target/*.jar /app/application.jar

EXPOSE 8082

LABEL maintainer="technoloqie.com.ec"

ENTRYPOINT ["java", "-jar", "application.jar"]
