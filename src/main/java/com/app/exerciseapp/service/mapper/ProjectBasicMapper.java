package com.app.exerciseapp.service.mapper;

import com.app.exerciseapp.domain.Project;
import com.app.exerciseapp.service.dto.ProjectBasicDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface ProjectBasicMapper extends EntityMapper<ProjectBasicDTO, Project> {
    @Mapping(target = "ownerName", source = "owner.userName")
    ProjectBasicDTO toDto(Project project);
}
