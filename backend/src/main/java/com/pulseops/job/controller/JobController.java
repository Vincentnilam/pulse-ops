package com.pulseops.job.controller;

import com.pulseops.job.dto.JobRequest;
import com.pulseops.job.dto.JobResponse;
import com.pulseops.job.entity.Job;
import com.pulseops.job.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/api/jobs")
    @ResponseStatus(HttpStatus.CREATED)
    public JobResponse createJob(@Valid @RequestBody JobRequest request) {
        Job job = jobService.createJob(request.getType());
        JobResponse response = new JobResponse();
        response.setId(job.getId().toString());
        response.setStatus(job.getStatus().name());
        response.setType(job.getType());
        response.setCreatedAt(job.getCreatedAt());
        return response;
    }
}
