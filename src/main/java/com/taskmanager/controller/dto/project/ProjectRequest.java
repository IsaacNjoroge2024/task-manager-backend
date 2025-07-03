package com.taskmanager.controller.dto.project;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;
}