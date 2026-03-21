package com.example.ttsiku.mapper;

import com.example.ttsiku.dto.TaskDTO;
import com.example.ttsiku.entity.Task;

public class TaskMapper {

    public static TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getTaskId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus() != null ? task.getStatus().name() : null,
                task.getUser() != null ? task.getUser().getUserId() : null,
                task.getUser() != null ? task.getUser().getUsername() : null,
                task.getProject() != null ? task.getProject().getProjectId() : null,
                task.getProject() != null ? task.getProject().getProjectName() : null
        );
    }
}