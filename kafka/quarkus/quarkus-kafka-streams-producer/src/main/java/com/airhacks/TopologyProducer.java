
package com.airhacks;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

/**
 *
 * @author airhacks.com
 */
@ApplicationScoped
public class TopologyProducer {

    @Outgoing("airhacks-topic")
    public Flowable<KafkaMessage<Integer, String>> numbers() {
        System.out.println("quarkus-kafka-streams-producer: topology instantiated");
        return Flowable.interval(1, TimeUnit.SECONDS).
                onBackpressureDrop()
                .map(t -> KafkaMessage.of(t.intValue(), t + " content"));
    }

}
