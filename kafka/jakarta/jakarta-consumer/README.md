# Build
mvn clean package && docker build -t com.airhacks/kafka-consumer .

# RUN

docker rm -f kafka-consumer || true && docker run -d -p 8080:8080 -p 4848:4848 --name kafka-consumer com.airhacks/kafka-consumer 