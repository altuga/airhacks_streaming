package sse;

import io.quarkus.scheduler.Scheduled;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;


@Path("hello")
public class SSEPublisher {
    
        private SseBroadcaster broadcaster;
        private Sse sse;
    
        @GET
        @Produces(MediaType.SERVER_SENT_EVENTS)
        public void register(@Context Sse sse, @Context SseEventSink eventSink) {
            this.sse = sse;
            if (broadcaster == null) {
                this.broadcaster = sse.newBroadcaster();
            }
            this.broadcaster.register(eventSink);
        }
    
        @Scheduled(every = "2s")
        public void beat() {
            System.out.println(".");
            if (this.sse == null) {
                return;
            }
            OutboundSseEvent outbound = this.sse.newEventBuilder().
                    id("" + System.currentTimeMillis()).
                    comment("featureTest").
                    name("time event").
                    data("time: " + System.currentTimeMillis()).
                    build();
    
            this.broadcaster.broadcast(outbound);
        }
    
}
    
