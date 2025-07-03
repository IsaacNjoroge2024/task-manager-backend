package com.taskmanager.controller;

import com.taskmanager.controller.assembler.TaskAssembler;
import com.taskmanager.controller.dto.task.TaskRequest;
import com.taskmanager.controller.dto.task.TaskResponse;
import com.taskmanager.controller.dto.task.TaskUpdateRequest;
import com.taskmanager.entity.Task;
import com.taskmanager.enums.TaskPriority;
import com.taskmanager.enums.TaskStatus;
import com.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskAssembler taskAssembler;

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) Long assigneeId,
            @RequestParam(required = false) Long projectId) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskService.findTasksWithFilters(status, priority, assigneeId, projectId, pageable);
        Page<TaskResponse> response = tasks.map(taskAssembler::toResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id) {
        Task task = taskService.findById(id);
        TaskResponse response = taskAssembler.toResponse(task);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request,
                                                   Authentication authentication) {
        Task task = taskService.create(request, authentication.getName());
        TaskResponse response = taskAssembler.toResponse(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id,
                                                   @Valid @RequestBody TaskUpdateRequest request) {
        Task task = taskService.update(id, request);
        TaskResponse response = taskAssembler.toResponse(task);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(@PathVariable Long id,
                                                         @RequestParam TaskStatus status) {
        Task task = taskService.updateStatus(id, status);
        TaskResponse response = taskAssembler.toResponse(task);
        return ResponseEntity.ok(response);
    }
}