FROM openjdk:17
COPY target/*.jar /app.jar
CMD ["ls /opt"]
ENTRYPOINT ["java","-jar", "/app.jar"]