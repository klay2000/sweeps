FROM openjdk:latest
WORKDIR /usr/src/progmmo
COPY ./target/server-0.0.1-SNAPSHOT.jar /usr/src/progmmo/server.jar
CMD [ "java", "-jar", "server.jar" ]
EXPOSE 443
