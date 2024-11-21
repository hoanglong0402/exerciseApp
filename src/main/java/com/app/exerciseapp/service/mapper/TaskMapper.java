package com.app.exerciseapp.service.mapper;

import com.app.exerciseapp.domain.Task;
import com.app.exerciseapp.service.dto.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {
    @Mapping(target = "assignedUserName", source = "assignedToUser.userName")
    TaskDTO toDto(Task project);
}
