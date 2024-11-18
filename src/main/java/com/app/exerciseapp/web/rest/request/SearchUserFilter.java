package com.app.exerciseapp.web.rest.request;

import com.app.exerciseapp.domain.enumeration.UserRole;
import lombok.Data;

@Data
public class SearchUserFilter {

    private UserRole userRole;
}
