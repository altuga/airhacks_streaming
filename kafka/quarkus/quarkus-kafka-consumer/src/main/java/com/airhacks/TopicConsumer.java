
package com.airhacks;

import io.quarkus.runtime.StartupEvent;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 *
 * @author airhacks.com
 */
@ApplicationScoped
public class TopicConsumer {

    private Properties props;
    private KafkaConsumer<Object, Object> consumer;

    @PostConstruct
    public void setup() {
        this.props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "airhacks-group");
        this.consumer = new KafkaConsumer<>(props);

    }

    public void consumer(@Observes StartupEvent event) {
        System.out.println("event = " + event);
        this.consumer.subscribe(Collections.singletonList("airhacks-topic"));
        while (true) {
            System.out.println("polling.");
            ConsumerRecords<Object, Object> records = consumer.poll(Duration.ofSeconds(1));
            records.forEach(r -> System.out.println(r.key() + " v; " + r.value() + " p: " + r.partition() + " o: " + r.offset()));
            consumer.commitSync();
        }

    }


}
