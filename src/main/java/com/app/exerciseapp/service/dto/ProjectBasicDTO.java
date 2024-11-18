package com.app.exerciseapp.service.dto;

import lombok.Data;

@Data
public class ProjectBasicDTO {

    private Long id;
    private String name;
    private String description;
    private Long ownerId;
    private String ownerName;
}
