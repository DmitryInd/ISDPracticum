FROM maven:3.8.5-jdk-11 AS build
COPY src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean package
ENTRYPOINT ["java","-jar","/target/web-file-storage-1.0-SNAPSHOT.jar"]