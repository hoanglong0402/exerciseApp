package com.app.exerciseapp.service.dto;

import com.app.exerciseapp.domain.enumeration.TaskStatus;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskDTO {

    private Long id;

    @NotNull
    private Long projectId;

    private Long assignedTo;
    private String assignedUserName;

    @NotNull
    @Size(min = 5, max = 50)
    private String title;

    @Size(min = 5, max = 200)
    private String description;

    @JsonSetter(nulls = Nulls.SKIP)
    private TaskStatus status = TaskStatus.NEW;
}
