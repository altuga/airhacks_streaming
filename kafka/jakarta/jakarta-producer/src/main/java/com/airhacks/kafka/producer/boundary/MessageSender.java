
package com.airhacks.kafka.producer.boundary;

import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author airhacks.com
 */
@Startup
@Singleton
public class MessageSender {

    private KafkaProducer<Object, Object> producer;

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
    }

    public void sendMessage(String message) {
        producer.beginTransaction();
        producer.send(new ProducerRecord<>("airhacks", "hey", message));
        producer.commitTransaction();
    }

    @PreDestroy
    public void cleanup() {
        producer.close();
    }



}
