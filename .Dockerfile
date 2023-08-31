FROM openjdk:11-jdk

WORKDIR /app

COPY target/client-0.0.1-SNAPSHOT.jar client.jar

EXPOSE 8001

CMD ["java", "-jar", "client.jar"]