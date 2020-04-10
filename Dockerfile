FROM openjdk:latest
WORKDIR /progmmo
ADD target/server-0.0.1-SNAPSHOT.jar server.jar
CMD java -jar server.jar
EXPOSE 80 443 8080
