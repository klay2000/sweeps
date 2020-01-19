FROM debian:stretch
WORKDIR /app
ENV WORLD_SEED 110101001101
RUN apt update && apt install git maven openjdk-8-jdk openjdk-8-jre -y
RUN git clone http://github.com/sarenord/sweeps && cd sweeps
CMD ["mvn", "install", "clean", "&&", "mvn", "install", "&&", "java -jar", "target/server-0.0.1-SNAPSHOT.jar"]
EXPOSE 443
