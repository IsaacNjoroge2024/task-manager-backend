package com.taskmanager.repository;

import com.taskmanager.entity.Task;
import com.taskmanager.enums.TaskPriority;
import com.taskmanager.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long projectId);

    List<Task> findByAssigneeId(Long assigneeId);

    List<Task> findByReporterId(Long reporterId);

    @Query("SELECT t FROM Task t WHERE " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:priority IS NULL OR t.priority = :priority) AND " +
            "(:assigneeId IS NULL OR t.assignee.id = :assigneeId) AND " +
            "(:projectId IS NULL OR t.project.id = :projectId)")
    Page<Task> findTasksWithFilters(
            @Param("status") TaskStatus status,
            @Param("priority") TaskPriority priority,
            @Param("assigneeId") Long assigneeId,
            @Param("projectId") Long projectId,
            Pageable pageable
    );
}