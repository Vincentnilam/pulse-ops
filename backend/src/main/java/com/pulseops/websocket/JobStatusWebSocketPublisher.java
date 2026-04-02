package com.pulseops.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class JobStatusWebSocketPublisher {

    private static final Logger log = LoggerFactory.getLogger(JobStatusWebSocketPublisher.class);
    private final SimpMessagingTemplate template;
    public static final String TOPIC = "/topic/jobs";

    public JobStatusWebSocketPublisher(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void publishJobStatusUpdate(JobStatusUpdateMessage message) {
        log.info("Publishing job status update: {} , status {}", message.getJobId(), message.getStatus());
        template.convertAndSend(TOPIC, message);
    }
}
