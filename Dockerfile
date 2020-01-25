FROM openjdk:latest
ENV WORLD_SEED 110101001101
COPY target/server-0.0.1-SNAPSHOT.jar server.jar
CMD [ "java", "-jar", "server.jar" ]
EXPOSE 443
