FROM maven:3.9-eclipse-temurin-25 AS compile
# RUN apt update && apt -y install git
COPY ./ /usr/src/mymaven/
WORKDIR /usr/src/mymaven
RUN ls -l
RUN mvn -Dmaven.test.skip=true package
RUN ls /usr/src/mymaven/target

FROM eclipse-temurin:25-jre
RUN mkdir /app
COPY --from=compile /usr/src/mymaven/target/*.jar /app/daam.jar
WORKDIR /app
USER 1000
EXPOSE 9000
ENTRYPOINT [ "java", "-jar", "daam.jar" ]