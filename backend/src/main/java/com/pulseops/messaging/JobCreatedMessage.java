package com.pulseops.messaging;

import java.util.UUID;

public class JobCreatedMessage {
    private UUID jobId;
    private String type;

    public UUID getJobId() {
        return jobId;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
