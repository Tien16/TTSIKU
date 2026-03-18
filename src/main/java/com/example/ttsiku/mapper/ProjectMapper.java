package com.example.ttsiku.mapper;

import com.example.ttsiku.dto.ProjectDTO;
import com.example.ttsiku.entity.Project;

public class ProjectMapper {

    public static ProjectDTO toDTO(Project project) {
        return new ProjectDTO(
                project.getProjectId(),
                project.getProjectName(),
                project.getDescription(),
                project.getStatus()
        );
    }
}