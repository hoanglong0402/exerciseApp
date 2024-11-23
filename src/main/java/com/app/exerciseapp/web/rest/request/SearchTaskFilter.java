package com.app.exerciseapp.web.rest.request;

import com.app.exerciseapp.domain.enumeration.TaskStatus;
import lombok.Data;

@Data
public class SearchTaskFilter {

    private String searchKeyword;
    private TaskStatus taskStatus;
    private Long userId;
}
