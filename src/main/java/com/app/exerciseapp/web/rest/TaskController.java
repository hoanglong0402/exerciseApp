package com.app.exerciseapp.web.rest;

import com.app.exerciseapp.service.TaskService;
import com.app.exerciseapp.service.dto.TaskDTO;
import com.app.exerciseapp.web.rest.request.SearchTaskFilter;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @PostMapping
    public TaskDTO createTask(@RequestBody @Valid TaskDTO task) {
        logger.info("into createTask: {}", task);
        return taskService.upsertTask(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        logger.info("into deleteTask: {}", id);
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<List<TaskDTO>> getAllTasksByProjectId(
        @PathVariable Long id,
        SearchTaskFilter searchTaskFilter,
        Pageable pageable
    ) {
        logger.info("into getAllTasks with proect_id {}: {}", id, searchTaskFilter);
        Page<TaskDTO> result = taskService.getTasksByProjectId(id, searchTaskFilter, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), result);
        return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
    }
}
