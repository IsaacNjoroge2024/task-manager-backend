package com.taskmanager.service;

import com.taskmanager.controller.dto.task.TaskRequest;
import com.taskmanager.controller.dto.task.TaskUpdateRequest;
import com.taskmanager.entity.Task;
import com.taskmanager.entity.User;
import com.taskmanager.entity.Project;
import com.taskmanager.entity.Category;
import com.taskmanager.enums.TaskPriority;
import com.taskmanager.enums.TaskStatus;
import com.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ProjectService projectService;
    private final CategoryRepository categoryRepository;
    private final WebSocketService webSocketService;

    public Page<Task> findTasksWithFilters(TaskStatus status, TaskPriority priority,
                                           Long assigneeId, Long projectId, Pageable pageable) {
        return taskRepository.findTasksWithFilters(status, priority, assigneeId, projectId, pageable);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public List<Task> findByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @Transactional
    public Task create(TaskRequest request, String reporterUsername) {
        User reporter = userService.findByUsername(reporterUsername);
        Project project = projectService.findById(request.getProjectId());

        User assignee = null;
        if (request.getAssigneeId() != null) {
            assignee = userService.findById(request.getAssigneeId());
        }

        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        }

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : TaskStatus.TODO)
                .priority(request.getPriority() != null ? request.getPriority() : TaskPriority.MEDIUM)
                .project(project)
                .assignee(assignee)
                .reporter(reporter)
                .category(category)
                .dueDate(request.getDueDate())
                .estimatedHours(request.getEstimatedHours())
                .build();

        Task savedTask = taskRepository.save(task);
        webSocketService.broadcastTaskUpdate(savedTask, "CREATE");
        return savedTask;
    }

    @Transactional
    public Task update(Long id, TaskUpdateRequest request) {
        Task task = findById(id);

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }
        if (request.getAssigneeId() != null) {
            User assignee = userService.findById(request.getAssigneeId());
            task.setAssignee(assignee);
        }
        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }
        if (request.getEstimatedHours() != null) {
            task.setEstimatedHours(request.getEstimatedHours());
        }
        if (request.getActualHours() != null) {
            task.setActualHours(request.getActualHours());
        }

        Task updatedTask = taskRepository.save(task);
        webSocketService.broadcastTaskUpdate(updatedTask, "UPDATE");
        return updatedTask;
    }

    @Transactional
    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
        webSocketService.broadcastTaskDelete(id);
    }

    @Transactional
    public Task updateStatus(Long id, TaskStatus status) {
        Task task = findById(id);
        task.setStatus(status);
        Task updatedTask = taskRepository.save(task);
        webSocketService.broadcastTaskUpdate(updatedTask, "UPDATE");
        return updatedTask;
    }
}