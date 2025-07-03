package com.taskmanager.controller.assembler;

import com.taskmanager.controller.dto.project.ProjectResponse;
import com.taskmanager.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectAssembler {

    public ProjectResponse toResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .ownerId(project.getOwner().getId())
                .ownerName(project.getOwner().getUsername())
                .active(project.getActive())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}