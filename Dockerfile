FROM gradle:8.2-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/pokedex-0.0.1.jar /app/pokedex-0.0.1.jar

ENTRYPOINT ["java","-jar","/app/pokedex-0.0.1.jar"]