package com.pulseops.job.service;

import com.pulseops.job.entity.Job;
import com.pulseops.job.entity.JobStatus;
import com.pulseops.job.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job createJob(String type) {
        Job job = new Job();
        job.setId(UUID.randomUUID());
        job.setType(type);
        job.setStatus(JobStatus.PENDING);
        job.setCreatedAt(LocalDateTime.now());
        return jobRepository.save(job);
    }
}
