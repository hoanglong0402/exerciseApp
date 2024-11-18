package com.app.exerciseapp.web.rest.request;

import lombok.Data;

@Data
public class SearchProjectFilter {

    private String searchKeyword;
    private Long ownerId;
}
