FROM openjdk:8
COPY ./target/pruebaeb-0.0.1-SNAPSHOT.jar pruebaeb-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","pruebaeb-0.0.1-SNAPSHOT.jar"]