package com.taskmanager.controller.dto.task;

import com.taskmanager.enums.TaskPriority;
import com.taskmanager.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Long projectId;
    private String projectName;
    private Long assigneeId;
    private String assigneeName;
    private Long reporterId;
    private String reporterName;
    private Long categoryId;
    private String categoryName;
    private LocalDate dueDate;
    private BigDecimal estimatedHours;
    private BigDecimal actualHours;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
}