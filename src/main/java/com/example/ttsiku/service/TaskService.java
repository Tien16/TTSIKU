package com.example.ttsiku.service;

import com.example.ttsiku.dto.PostTaskDto;
import com.example.ttsiku.dto.TaskDTO;
import com.example.ttsiku.entity.Project;
import com.example.ttsiku.entity.Task;
import com.example.ttsiku.entity.User;
import com.example.ttsiku.mapper.TaskMapper;
import com.example.ttsiku.repository.ProjectRepository;
import com.example.ttsiku.repository.TaskRepository;
import com.example.ttsiku.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskDTO create(PostTaskDto request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setUser(user);
        task.setProject(project);

        return TaskMapper.toDTO(taskRepository.save(task));
    }

    public List<TaskDTO> getAll() {
        return taskRepository.findAll()
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getByUser(Integer userId) {
        return taskRepository.findByUserUserId(userId)
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getByProject(Integer projectId) {
        return taskRepository.findByProjectProjectId(projectId)
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }
}