package com.pranathi.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskUpdateRequest {
    @NotBlank(message = "Status cannot be empty")
    private String status;

    @Size(max = 500)
    private String description;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
