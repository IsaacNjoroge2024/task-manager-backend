package com.taskmanager.controller.dto.task;

import com.taskmanager.enums.TaskPriority;
import com.taskmanager.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TaskRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private TaskStatus status;
    private TaskPriority priority;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    private Long assigneeId;
    private Long categoryId;
    private LocalDate dueDate;
    private BigDecimal estimatedHours;
}