package com.app.exerciseapp.service.dto;

import com.app.exerciseapp.domain.enumeration.UserRole;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String userName;
    private String email;
    private UserRole role;
}
