package com.taskmanager.controller.dto.project;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private Long ownerId;
    private String ownerName;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}