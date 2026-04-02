package com.pulseops.job.dto;

import jakarta.validation.constraints.NotBlank;

public class JobRequest {

    @NotBlank
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
