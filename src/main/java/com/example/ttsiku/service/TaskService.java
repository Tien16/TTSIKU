package com.example.ttsiku.service;

import com.example.ttsiku.dto.PostTaskDto;
import com.example.ttsiku.dto.TaskDTO;
import com.example.ttsiku.entity.Project;
import com.example.ttsiku.entity.Task;
import com.example.ttsiku.entity.User;
import com.example.ttsiku.enums.TaskStatus;
import com.example.ttsiku.exception.NotFoundException;
import com.example.ttsiku.mapper.TaskMapper;
import com.example.ttsiku.repository.ProjectRepository;
import com.example.ttsiku.repository.ProjectUserRepository;
import com.example.ttsiku.repository.TaskRepository;
import com.example.ttsiku.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    @Autowired
    private  TaskRepository taskRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  ProjectRepository projectRepository;
    @Autowired
    private ProjectUserRepository projectUserRepository;

    public TaskDTO create(PostTaskDto request) {

        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new RuntimeException("Title không được để trống");
        }

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = null;
        if (request.getUserId() != null) {
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        if (request.getStatus() == null) {
            task.setStatus(TaskStatus.TODO);
        } else {
            try {
                task.setStatus(TaskStatus.valueOf(request.getStatus()));
            } catch (Exception e) {
                throw new RuntimeException("Status không hợp lệ");
            }
        }

        task.setUser(user);
        task.setProject(project);

        return TaskMapper.toDTO(taskRepository.save(task));
    }

    public PostTaskDto postTaskDto(PostTaskDto request) {

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new NotFoundException("Project không tồn tại"));

        User user = null;
        if (request.getUserId() != null) {
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadline(request.getDeadline());

        if (request.getStatus() == null) {
            task.setStatus(TaskStatus.TODO);
        } else {
            try {
                task.setStatus(TaskStatus.valueOf(request.getStatus()));
            } catch (Exception e) {
                throw new RuntimeException("Status không hợp lệ");
            }
        }

        task.setUser(user);
        task.setProject(project);

        Task saved = taskRepository.save(task);

        PostTaskDto response = new PostTaskDto();
        response.setTitle(saved.getTitle());
        response.setDescription(saved.getDescription());
        response.setStatus(saved.getStatus().name());
        response.setProjectId(saved.getProject().getProjectId());
        response.setUserId(
                saved.getUser() != null ? saved.getUser().getUserId() : null
        );
        response.setDeadline(saved.getDeadline());

        return response;
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

    public TaskDTO assignTask(Integer taskId, Integer userId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (task.getStatus().name().equals("DONE")) {
            throw new RuntimeException("Task đã DONE, không thể assign");
        }

        boolean isMember = projectUserRepository
                .existsByProjectProjectIdAndUserUserId(
                        task.getProject().getProjectId(),
                        userId
                );

        if (!isMember) {
            throw new RuntimeException("User không thuộc project");
        }

        task.setUser(user);

        return TaskMapper.toDTO(taskRepository.save(task));
    }

    public TaskDTO putStatus(Integer taskId, String newStatus) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (task.getStatus().name().equals("DONE")) {
            throw new RuntimeException("Task đã DONE, không thể update");
        }

        String currentStatus = task.getStatus().name();

        if (currentStatus.equals("TODO") && newStatus.equals("IN_PROGRESS")) {
        } else if (currentStatus.equals("IN_PROGRESS") && newStatus.equals("DONE")) {
        } else {
            throw new RuntimeException("Sai flow status");
        }

        task.setStatus(TaskStatus.valueOf(newStatus));

        return TaskMapper.toDTO(taskRepository.save(task));
    }

    public List<TaskDTO> getMyTasks(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findByUserUserId(user.getUserId())
                .stream()
                .map(TaskMapper::toDTO)
                .toList();
    }
}