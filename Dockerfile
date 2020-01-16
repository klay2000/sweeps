FROM debian:latest
WORKDIR /app
ENV WORLD_SEED 110101001101
RUN git clone http://github.com/sarenord/sweeps
RUN cd sweeps
CMD ["mvn", "install", "&&", "java", "-jar", "target/"]
