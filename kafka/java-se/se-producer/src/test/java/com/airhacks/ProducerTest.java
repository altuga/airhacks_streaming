package com.airhacks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Before;
import org.junit.Test;

public class ProducerTest {

    private Properties props;
    private Producer<String, String> producer;
    private AdminClient admin;

    @Before
    public void setup() {
        this.props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<>(props);
        this.admin = AdminClient.create(this.props);
    }

    public boolean topicExists(String name) {
        ListTopicsResult topicsFuture = this.admin.listTopics();
        Set<String> topics;
        try {
            topics = topicsFuture.names().get();
            System.out.println("Topic exists? " + topics);
            return topics.contains(name);
        } catch (InterruptedException | ExecutionException ex) {
            throw new IllegalStateException("Fetch topic names failed", ex);
        }

    }

    public void deleteTopic(String name) throws InterruptedException, ExecutionException {
        DeleteTopicsResult result = this.admin.deleteTopics(Collections.singleton(name));
        result.all().get();
        result.values().forEach((r, v) -> System.out.println(r + " " + v));
    }

    public void createTopic(String name) throws InterruptedException, ExecutionException {
        NewTopic newTopic = new NewTopic(name, 2, (short) 1);

        List<NewTopic> newTopics = new ArrayList<NewTopic>();
        newTopics.add(newTopic);
        CreateTopicsResult result = this.admin.createTopics(newTopics);
        result.all().get();
        result.values().
                entrySet().
                forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));
        System.out.println("Topic: " + name + " created");
    }

    @Test
    public void produce() throws InterruptedException, ExecutionException {
        String topicName = "airhacks-topic";
        if (!topicExists(topicName)) {
            createTopic(topicName);
        }

        for (int i = 0; i < 10000; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>(topicName, 1, System.currentTimeMillis(), "key", "value " + i);
            Thread.sleep(100);
            System.out.println("Sending...");
            RecordMetadata data = this.producer.send(record).get();
            System.out.println("Data: " + data.offset() + " partition: " + data.partition() + " " + data.timestamp());
        }
        this.producer.close();
    }

}
