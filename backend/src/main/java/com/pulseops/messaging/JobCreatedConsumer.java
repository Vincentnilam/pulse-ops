package com.pulseops.messaging;

import com.pulseops.config.RabbitMqConfig;
import com.pulseops.job.entity.Job;
import com.pulseops.job.entity.JobStatus;
import com.pulseops.job.repository.JobRepository;
import com.pulseops.websocket.JobStatusUpdateMessage;
import com.pulseops.websocket.JobStatusWebSocketPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class JobCreatedConsumer {

    private static final Logger log = LoggerFactory.getLogger(JobCreatedConsumer.class);

    private final JobRepository jobRepository;
    private final JobStatusWebSocketPublisher wsPublisher;

    public JobCreatedConsumer(JobRepository jobRepository, JobStatusWebSocketPublisher wsPublisher) {
        this.jobRepository = jobRepository;
        this.wsPublisher = wsPublisher;
    }

    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void handleJobCreated(JobCreatedMessage message) {
        Job job = jobRepository.findById(message.getJobId()).orElse(null);
        if (job == null) {
            log.error("Job not found: {}", message.getJobId());
            return;
        }
        job.setStatus(JobStatus.RUNNING);
        jobRepository.save(job);
        // send to websocket
        publishStatusUpdate(job);

        // simulate work
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error("Thread interrupted", e);
            Thread.currentThread().interrupt();
            job.setStatus(JobStatus.FAILED);
            jobRepository.save(job);
            publishStatusUpdate(job);

            return;
        }
        job.setStatus(JobStatus.COMPLETED);
        jobRepository.save(job);
        publishStatusUpdate(job);
    }

    private void publishStatusUpdate(Job job) {
        JobStatusUpdateMessage statusUpdate = new JobStatusUpdateMessage();
        statusUpdate.setJobId(job.getId());
        statusUpdate.setType(job.getType());
        statusUpdate.setStatus(job.getStatus().name());
        wsPublisher.publishJobStatusUpdate(statusUpdate);
    }

}
