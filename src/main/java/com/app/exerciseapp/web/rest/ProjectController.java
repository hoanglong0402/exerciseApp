package com.app.exerciseapp.web.rest;

import com.app.exerciseapp.service.ProjectService;
import com.app.exerciseapp.service.dto.ProjectBasicDTO;
import com.app.exerciseapp.web.rest.request.SearchProjectFilter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectBasicDTO>> getAllProjects(Pageable pageable, SearchProjectFilter searchProjectFilter) {
        Page<ProjectBasicDTO> result = projectService.getProjects(pageable, searchProjectFilter);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), result);
        return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ProjectBasicDTO getProjectDetail(@PathVariable Long id) {
        ProjectBasicDTO result = projectService.getBasicProject(id);
        return result;
    }
}
