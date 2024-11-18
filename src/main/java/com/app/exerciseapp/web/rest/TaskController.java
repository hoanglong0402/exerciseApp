package com.app.exerciseapp.web.rest;

import com.app.exerciseapp.service.TaskService;
import com.app.exerciseapp.service.dto.TaskDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //    @GetMapping
    //    public ResponseEntity<List<ProjectBasicDTO>> getAllUsers(Pageable pageable, SearchUserFilter searchUserFilter) {
    //        Page<ProjectBasicDTO> result = projectService.getProjects(pageable, searchProjectFilter);
    //        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), result);
    //        return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
    //    }

    @PostMapping
    public TaskDTO createTask(@RequestBody @Valid TaskDTO task) {
        return taskService.upsertTask(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
