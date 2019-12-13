
package com.airhacks.kafka.consumer.control;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

/**
 *
 * @author airhacks.com
 */
@Stateless
public class EventSink {

    public void onMessage(@Observes String message) {
        System.out.println("message = " + message);
    }

}
