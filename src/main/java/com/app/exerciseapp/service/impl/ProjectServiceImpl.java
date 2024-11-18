package com.app.exerciseapp.service.impl;

import com.app.exerciseapp.domain.Project;
import com.app.exerciseapp.repository.ProjectRepository;
import com.app.exerciseapp.service.ProjectService;
import com.app.exerciseapp.service.dto.ProjectBasicDTO;
import com.app.exerciseapp.service.mapper.ProjectBasicMapper;
import com.app.exerciseapp.web.rest.request.SearchProjectFilter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectBasicMapper projectBasicMapper;

    @Override
    public Page<ProjectBasicDTO> getProjects(Pageable pageable, SearchProjectFilter filter) {
        Page<Project> result = projectRepository.findAllProjectsWithFilter(filter.getSearchKeyword(), filter.getOwnerId(), pageable);
        return result.map(projectBasicMapper::toDto);
    }

    @Override
    public ProjectBasicDTO getBasicProject(Long id) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project != null) {
            return projectBasicMapper.toDto(project);
        }
        return null;
    }
}
