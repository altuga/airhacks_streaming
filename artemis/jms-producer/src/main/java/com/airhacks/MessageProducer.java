
package com.airhacks;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.Session;

@ApplicationScoped
public class MessageProducer {

    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    JMSContext context;

    @Resource(mappedName = "jms/workshops")
    Queue queue;

    public void send(String message) {
        try ( JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            Queue queue = context.createQueue("workshops");
            JMSProducer producer = context.createProducer();
            producer.send(queue, message);
        }
    }
}
