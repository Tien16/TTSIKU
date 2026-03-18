package com.example.ttsiku.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private Integer taskId;
    private String title;
    private String description;
    private String status;

    private Integer userId;
    private String username;

    private Integer projectId;
    private String projectName;
}