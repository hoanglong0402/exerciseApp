package com.app.exerciseapp.service.impl;

import com.app.exerciseapp.domain.Project;
import com.app.exerciseapp.domain.Task;
import com.app.exerciseapp.domain.User;
import com.app.exerciseapp.repository.ProjectRepository;
import com.app.exerciseapp.repository.TaskRepository;
import com.app.exerciseapp.repository.UserRepository;
import com.app.exerciseapp.service.TaskService;
import com.app.exerciseapp.service.dto.TaskDTO;
import com.app.exerciseapp.service.mapper.TaskMapper;
import com.app.exerciseapp.web.rest.errors.BadRequestAlertException;
import jakarta.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TaskDTO upsertTask(TaskDTO task) {
        Project project = projectRepository.findById(task.getProjectId()).orElse(null);
        if (Objects.isNull(project)) {
            throw new BadRequestAlertException("Project Id not found", "TaskDTO", "project.id.not.found");
        }
        Task taskEntity;
        User userEntity = null;
        if (Objects.nonNull(task.getAssignedTo())) {
            Optional<User> user = userRepository.findById(task.getAssignedTo());
            if (user.isEmpty()) {
                throw new BadRequestAlertException("User Id not found", "TaskDTO", "user.id.not.found");
            }
            userEntity = user.orElseThrow();
        }
        if (Objects.isNull(task.getId())) {
            taskEntity = taskMapper.toEntity(task);
            taskEntity.setProject(project);
            taskEntity.setAssignedToUser(userEntity);
        } else {
            taskEntity = taskRepository.findById(task.getId()).orElse(null);
            if (Objects.isNull(taskEntity)) {
                throw new BadRequestAlertException("Task not found", "TaskDTO", "task.id.not.found");
            }
            BeanUtils.copyProperties(task, taskEntity);
            taskEntity.setProject(project);
            taskEntity.setAssignedToUser(userEntity);
        }
        taskEntity = taskRepository.save(taskEntity);
        return taskMapper.toDto(taskEntity);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (Objects.isNull(task)) {
            throw new BadRequestAlertException("Task not found", "TaskDTO", "task.id.not.found");
        }
        taskRepository.delete(task);
    }

    @Override
    public Page<TaskDTO> getTasks(Pageable pageable) {
        Page<Task> result = taskRepository.findAllProjectsWithFilter(pageable);
        return result.map(taskMapper::toDto);
    }
}
