
package com.airhacks.kafka.consumer.control;

import java.util.Arrays;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 *
 * @author airhacks.com
 */
@Startup
@Singleton
public class KafkaConsumerInitializer {

    private Consumer<String, String> consumer;

    @Inject
    Event<String> events;

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<>(props);

    }

    @Schedule(second = "*/2", minute = "*", hour = "*")
    public void retrieve() {
        System.out.println(".");
        consumer.subscribe(Arrays.asList("airhacks"));
        ConsumerRecords<String, String> records = consumer.poll(100);
        for (ConsumerRecord<String, String> record : records) {
            String message = record.key() + ":" + record.value();
            System.out.println(message);
            events.fire(message);
        }
    }

    @PreDestroy
    public void shutdown() {
        consumer.close();
    }

}
