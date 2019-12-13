package com.airhacks.jms.consumer.boundary;

import io.quarkus.runtime.StartupEvent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.Session;

/**
 *
 * @author airhacks.com
 */
@ApplicationScoped
public class MessageConsumer {

    @Inject
    ConnectionFactory connectionFactory;

    void onStart(@Observes StartupEvent ev) {
        System.out.println("starting...");
        JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE);
        Queue queue = context.createQueue("workshops");
        JMSConsumer consumer = context.createConsumer(queue);
        while (true) {
            String message = consumer.receiveBody(String.class);
            System.out.println("message = " + message);
        }
    }

}
