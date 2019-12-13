package com.airhacks;

import java.util.Date;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("send")
public class HelloResource {

    @Inject
    MessageProducer producer;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        this.producer.send("hello " + new Date());
        return "message sent";
    }
}