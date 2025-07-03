package com.taskmanager.controller;

import com.taskmanager.controller.assembler.ProjectAssembler;
import com.taskmanager.controller.dto.project.ProjectRequest;
import com.taskmanager.controller.dto.project.ProjectResponse;
import com.taskmanager.entity.Project;
import com.taskmanager.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectAssembler projectAssembler;

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        List<Project> projects = projectService.findAll();
        List<ProjectResponse> response = projects.stream()
                .map(projectAssembler::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long id) {
        Project project = projectService.findById(id);
        ProjectResponse response = projectAssembler.toResponse(project);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest request,
                                                         Authentication authentication) {
        Project project = projectService.create(request, authentication.getName());
        ProjectResponse response = projectAssembler.toResponse(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id,
                                                         @Valid @RequestBody ProjectRequest request) {
        Project project = projectService.update(id, request);
        ProjectResponse response = projectAssembler.toResponse(project);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}