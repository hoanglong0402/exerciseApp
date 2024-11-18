package com.app.exerciseapp.web.rest;

import com.app.exerciseapp.service.ProjectService;
import com.app.exerciseapp.service.UserService;
import com.app.exerciseapp.service.dto.ProjectBasicDTO;
import com.app.exerciseapp.service.dto.UserDTO;
import com.app.exerciseapp.web.rest.request.SearchProjectFilter;
import com.app.exerciseapp.web.rest.request.SearchUserFilter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable, SearchUserFilter searchUserFilter) {
        Page<UserDTO> result = userService.getUsers(pageable, searchUserFilter);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), result);
        return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
    }
}
