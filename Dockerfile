FROM openjdk:latest
WORKDIR /
ADD target/server-0.0.1-SNAPSHOT.jar server.jar
EXPOSE 443
CMD java -jar server.jar
