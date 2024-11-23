package com.app.exerciseapp.repository;

import com.app.exerciseapp.domain.Task;
import com.app.exerciseapp.domain.enumeration.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(
        "SELECT t FROM Task t LEFT JOIN FETCH t.assignedToUser " +
        "WHERE t.projectId = :projectId " +
        "and (:search IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :search, '%'))) " +
        "and (:taskStatus is null or t.status = :taskStatus) " +
        "and (:userId is null or t.assignedTo = :userId) "
    )
    Page<Task> findAllProjectsWithFilter(Long projectId, String search, TaskStatus taskStatus, Long userId, Pageable pageable);
}
