FROM openjdk:latest
WORKDIR /
ADD target/server-0.0.1-SNAPSHOT.jar server.jar
CMD java -jar server.jar
EXPOSE 443
