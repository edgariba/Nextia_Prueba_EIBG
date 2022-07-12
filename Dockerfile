FROM openjdk:8
COPY ./target/PruebaEb-0.0.1-SNAPSHOT.jar PruebaEb-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","PruebaEb-0.0.1-SNAPSHOT.jar"]