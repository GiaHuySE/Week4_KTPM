FROM openjdk:11
COPY target/product.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]