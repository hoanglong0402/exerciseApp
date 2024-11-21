package com.app.exerciseapp.repository;

import com.app.exerciseapp.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t JOIN FETCH t.assignedToUser ")
    Page<Task> findAllProjectsWithFilter(Pageable pageable);
}
