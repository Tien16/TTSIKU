package com.example.ttsiku.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Integer projectId;
    private String projectName;
    private String description;
    private String status;
}