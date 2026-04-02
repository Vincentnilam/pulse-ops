package com.pulseops.messaging;

import com.pulseops.config.RabbitMqConfig;
import com.pulseops.job.entity.Job;
import com.pulseops.job.entity.JobStatus;
import com.pulseops.job.repository.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class JobCreatedConsumer {

    private static final Logger log = LoggerFactory.getLogger(JobCreatedConsumer.class);

    private final JobRepository jobRepository;

    public JobCreatedConsumer(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
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
        // simulate work
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error("Thread interrupted", e);
            Thread.currentThread().interrupt();
            job.setStatus(JobStatus.FAILED);
            jobRepository.save(job);
            return;
        }
        job.setStatus(JobStatus.COMPLETED);
        jobRepository.save(job);
    }


}
