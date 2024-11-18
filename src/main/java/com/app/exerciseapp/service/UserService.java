package com.app.exerciseapp.service;

import com.app.exerciseapp.service.dto.UserDTO;
import com.app.exerciseapp.web.rest.request.SearchUserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDTO> getUsers(Pageable pageable, SearchUserFilter search);
}
