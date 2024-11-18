package com.app.exerciseapp.service.mapper;

import com.app.exerciseapp.domain.Task;
import com.app.exerciseapp.domain.User;
import com.app.exerciseapp.service.dto.TaskDTO;
import com.app.exerciseapp.service.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {}
