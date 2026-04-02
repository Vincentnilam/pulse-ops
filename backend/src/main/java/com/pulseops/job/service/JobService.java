package com.pulseops.job.service;

import com.pulseops.job.entity.Job;
import com.pulseops.job.entity.JobStatus;
import com.pulseops.job.repository.JobRepository;
import com.pulseops.messaging.JobCreatedMessage;
import com.pulseops.messaging.JobPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final JobPublisher jobPublisher;

    public JobService(JobRepository jobRepository, JobPublisher jobPublisher) {
        this.jobRepository = jobRepository;
        this.jobPublisher = jobPublisher;
    }

    public Job createJob(String type) {
        Job job = new Job();
        job.setId(UUID.randomUUID());
        job.setType(type);
        job.setStatus(JobStatus.PENDING);
        job.setCreatedAt(LocalDateTime.now());
        job = jobRepository.save(job);

        JobCreatedMessage message = new JobCreatedMessage();
        message.setJobId(job.getId());
        message.setType(job.getType());
        jobPublisher.publishJobCreated(message);

        return job;
    }

    public Job getJobById(UUID id) {
        return jobRepository.findById(id).orElse(null);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}
