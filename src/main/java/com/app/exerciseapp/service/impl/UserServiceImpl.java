package com.app.exerciseapp.service.impl;

import com.app.exerciseapp.domain.User;
import com.app.exerciseapp.repository.UserRepository;
import com.app.exerciseapp.service.UserService;
import com.app.exerciseapp.service.dto.UserDTO;
import com.app.exerciseapp.service.mapper.UserMapper;
import com.app.exerciseapp.web.rest.request.SearchUserFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Page<UserDTO> getUsers(Pageable pageable, SearchUserFilter filter) {
        Page<User> result = userRepository.findAllUsersWithFilter(filter.getUserRole(), pageable);
        return result.map(userMapper::toDto);
    }
}
