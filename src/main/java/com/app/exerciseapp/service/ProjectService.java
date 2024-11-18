package com.app.exerciseapp.service;

import com.app.exerciseapp.service.dto.ProjectBasicDTO;
import com.app.exerciseapp.web.rest.request.SearchProjectFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    Page<ProjectBasicDTO> getProjects(Pageable pageable, SearchProjectFilter search);

    ProjectBasicDTO getBasicProject(Long id);
}
