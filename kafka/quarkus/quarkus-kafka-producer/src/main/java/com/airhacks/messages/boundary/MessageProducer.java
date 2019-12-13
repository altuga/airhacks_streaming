
package com.airhacks.messages.boundary;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

@ApplicationScoped
public class MessageProducer {

    private Properties props;
    private Producer<String, String> producer;

    @PostConstruct
    public void init() {
        this.props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<>(props);
    }

    public void send(String topicName, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, 1, System.currentTimeMillis(), "key", message);
        System.out.println("Sending...");
        RecordMetadata data;
        try {
            data = this.producer.send(record).get();
            System.out.println("Data: " + data.offset() + " partition: " + data.partition() + " " + data.timestamp());

        } catch (InterruptedException | ExecutionException ex) {
            System.err.println("ex" + ex);
        }
    }


}
