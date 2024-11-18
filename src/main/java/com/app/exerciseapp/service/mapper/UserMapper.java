package com.app.exerciseapp.service.mapper;

import com.app.exerciseapp.domain.User;
import com.app.exerciseapp.service.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserDTO, User> {}
