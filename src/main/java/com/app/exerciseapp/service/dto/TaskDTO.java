package com.app.exerciseapp.service.dto;

import com.app.exerciseapp.domain.enumeration.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskDTO {

    private Long id;

    @NotNull
    private Long projectId;

    private Long assignedTo;
    private String title;
    private String description;
    private TaskStatus status = TaskStatus.NEW;
}
