package com.app.exerciseapp.service;

import com.app.exerciseapp.service.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskDTO upsertTask(TaskDTO task);

    void deleteTask(Long id);

    Page<TaskDTO> getTasks(Pageable pageable);
}
