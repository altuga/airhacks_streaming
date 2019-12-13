package com.airhacks.messages.boundary;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("messages")
public class MessagesResource {

    @Inject
    MessageProducer producer;

    @POST
    @Path("{topic-name}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void hello(@PathParam("topic-name") String topicName, String message) {
        this.producer.send(topicName, message);
    }

    @GET
    public String command() {
        return "curl -XPOST -d'hey joe' localhost:8080/messages/airhacks";
    }

}