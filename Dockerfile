FROM maven:3.8.3-openjdk-11 AS maven_builder
MAINTAINER henriquecesarzup
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean compile generate-sources -DskipTests

FROM openjdk:11
MAINTAINER henriquecesar
COPY --from=maven_builder /usr/src/app/target/*.jar api.jar
ENTRYPOINT ["java", "-jar", "/api.jar"]
