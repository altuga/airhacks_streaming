# Build
mvn clean package && docker build -t com.airhacks/kafka-producer .

# RUN

docker rm -f kafka-producer || true && docker run -d -p 8080:8080 -p 4848:4848 --name kafka-producer com.airhacks/kafka-producer curl -XPOST -d'hello' localhost:8080/kafka-producer/resources/messages
