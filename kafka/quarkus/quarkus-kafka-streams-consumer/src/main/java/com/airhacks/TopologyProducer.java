
package com.airhacks;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;

/**
 *
 * @author airhacks.com
 */
@ApplicationScoped
public class TopologyProducer {



    @Produces
    public Topology topology() {
        System.out.println("quarkus-kafka-streams-consumer: topology instantiated");
        final Serde<String> stringSerde = Serdes.String();
        StreamsBuilder builder = new StreamsBuilder();
        builder.stream("airhacks-output-topic", Consumed.with(stringSerde, stringSerde)).
                peek((k, v) -> System.out.println("key " + k + " value  " + v)).
                mapValues(v -> "thanks, forwarding: " + v).
                to("airhacks-the-end", Produced.with(stringSerde, stringSerde));

        return builder.build();
    }

}
