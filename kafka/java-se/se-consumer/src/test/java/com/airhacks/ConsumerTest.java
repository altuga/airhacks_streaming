package com.airhacks;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Before;
import org.junit.Test;

public class ConsumerTest {

    private Properties props;
    private Consumer<String, String> consumer;
    private AdminClient admin;

    @Before
    public void setup() {
        this.props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "airhacks-group");

        this.consumer = new KafkaConsumer<>(props);
    }

    @Test
    public void consumer() {
        this.consumer.subscribe(Collections.singletonList("airhacks-topic"));
        while (true) {
            System.out.println("polling.");
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            records.forEach(r -> System.out.println(r.key() + " v; " + r.value() + " p: " + r.partition() + " o: " + r.offset()));
            consumer.commitSync();
        }

    }

}
