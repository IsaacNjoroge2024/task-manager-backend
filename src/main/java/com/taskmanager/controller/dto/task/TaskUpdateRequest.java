package com.taskmanager.controller.dto.task;

import com.taskmanager.enums.TaskPriority;
import com.taskmanager.enums.TaskStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TaskUpdateRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Long assigneeId;
    private LocalDate dueDate;
    private BigDecimal estimatedHours;
    private BigDecimal actualHours;
}