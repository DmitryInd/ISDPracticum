FROM maven:3.5-jdk-11 AS build
COPY src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean package
RUN ls /target/ -a

FROM gcr.io/distroless/java
COPY --from=build /target/web-file-storage-1.0-SNAPSHOT.jar web-file-storage-1.0-SNAPSHOT.jar
COPY --from=build /target/lib /lib
COPY --from=build /src/main/webapp /src/main/webapp
ENTRYPOINT ["java","-jar","/web-file-storage-1.0-SNAPSHOT.jar"]