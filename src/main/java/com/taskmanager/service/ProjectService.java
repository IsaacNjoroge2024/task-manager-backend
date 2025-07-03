package com.taskmanager.service;

import com.taskmanager.controller.dto.project.ProjectRequest;
import com.taskmanager.entity.Project;
import com.taskmanager.entity.User;
import com.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
    }

    public List<Project> findByOwnerId(Long ownerId) {
        return projectRepository.findByOwnerId(ownerId);
    }

    @Transactional
    public Project create(ProjectRequest request, String ownerUsername) {
        User owner = userService.findByUsername(ownerUsername);

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .owner(owner)
                .build();

        return projectRepository.save(project);
    }

    @Transactional
    public Project update(Long id, ProjectRequest request) {
        Project project = findById(id);

        project.setName(request.getName());
        project.setDescription(request.getDescription());

        return projectRepository.save(project);
    }

    @Transactional
    public void delete(Long id) {
        Project project = findById(id);
        projectRepository.delete(project);
    }
}