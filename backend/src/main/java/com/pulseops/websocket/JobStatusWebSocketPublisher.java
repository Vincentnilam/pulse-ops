package com.pulseops.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class JobStatusWebSocketPublisher {

    private final SimpMessagingTemplate template;
    public static final String TOPIC = "/topic/jobs";

    public JobStatusWebSocketPublisher(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void publishJobStatusUpdate(JobStatusUpdateMessage message) {
        template.convertAndSend(TOPIC, message);
    }
}
