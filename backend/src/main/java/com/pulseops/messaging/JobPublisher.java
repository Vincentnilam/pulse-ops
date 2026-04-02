package com.pulseops.messaging;

import com.pulseops.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class JobPublisher {

    private final RabbitTemplate rabbitTemplate;

    public JobPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishJobCreated(JobCreatedMessage message) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME,
                RabbitMqConfig.JOB_CREATED_ROUTING_KEY,
                message);
    }


}
