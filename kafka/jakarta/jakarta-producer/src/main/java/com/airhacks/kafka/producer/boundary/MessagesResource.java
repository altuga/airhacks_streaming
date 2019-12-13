
package com.airhacks.kafka.producer.boundary;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author airhacks.com
 */
@Path("messages")
public class MessagesResource {
    
    @Inject
    MessageSender sender;

    @POST
    public void send(String message) {
        this.sender.sendMessage(message);
    }

}
