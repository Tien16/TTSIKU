package com.example.ttsiku.dto;

import lombok.*;

@Getter @Setter
public class PostTaskDto {

    private String title;
    private String description;
    private String status;

    private Integer userId;
    private Integer projectId;
}