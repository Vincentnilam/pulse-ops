package com.pulseops.job.controller;

import com.pulseops.job.dto.JobRequest;
import com.pulseops.job.dto.JobResponse;
import com.pulseops.job.entity.Job;
import com.pulseops.job.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        return mapToResponse(job);
    }

    @GetMapping("/api/jobs/{id}")
    public JobResponse getJobById(@PathVariable UUID id) {
        Job job = jobService.getJobById(id);
        if (job == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found");
        }
        return mapToResponse(job);
    }

    @GetMapping("/api/jobs")
    public List<JobResponse> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        List<JobResponse> response = new ArrayList<>();
        jobs.forEach(job -> response.add(mapToResponse(job)));
        return response;

    }

    private JobResponse mapToResponse(Job job) {
        JobResponse response = new JobResponse();
        response.setId(job.getId().toString());
        response.setStatus(job.getStatus().name());
        response.setType(job.getType());
        response.setCreatedAt(job.getCreatedAt());
        return response;
    }
}
