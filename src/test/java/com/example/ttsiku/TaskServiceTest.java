package com.example.ttsiku;

import com.example.ttsiku.dto.PostTaskDto;
import com.example.ttsiku.dto.TaskDTO;
import com.example.ttsiku.entity.Project;
import com.example.ttsiku.entity.Task;
import com.example.ttsiku.entity.User;
import com.example.ttsiku.enums.TaskStatus;
import com.example.ttsiku.repository.ProjectRepository;
import com.example.ttsiku.repository.ProjectUserRepository;
import com.example.ttsiku.repository.TaskRepository;
import com.example.ttsiku.repository.UserRepository;
import com.example.ttsiku.service.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectUserRepository projectUserRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // =====================
    // TEST CREATE SUCCESS
    // =====================
    @Test
    void testCreateTask_Success() {

        PostTaskDto request = new PostTaskDto();
        request.setTitle("Test Task");
        request.setDescription("Task description");
        request.setProjectId(1);
        request.setUserId(10);
        request.setStatus("TODO");

        Project project = new Project();
        project.setProjectId(1);

        User user = new User();
        user.setUserId(10);

        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        when(userRepository.findById(10)).thenReturn(Optional.of(user));

        Task saved = new Task();
        saved.setTaskId(100);
        saved.setTitle("Test Task");
        saved.setDescription("Task description");
        saved.setStatus(TaskStatus.TODO);
        saved.setProject(project);
        saved.setUser(user);

        when(taskRepository.save(any(Task.class))).thenReturn(saved);

        TaskDTO result = taskService.create(request);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Task");
        assertThat(result.getDescription()).isEqualTo("Task description");
        assertThat(result.getStatus()).isEqualTo("TODO");
        assertThat(result.getProjectId()).isEqualTo(1);
        assertThat(result.getUserId()).isEqualTo(10);
    }

    // ============================
    // CREATE - PROJECT NOT FOUND
    // ============================
    @Test
    void testCreateTask_ProjectNotFound() {

        PostTaskDto request = new PostTaskDto();
        request.setTitle("A");
        request.setProjectId(999);

        Mockito.when(projectRepository.findById(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.create(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Project not found");
    }

    // ============================
    // CREATE - TITLE EMPTY
    // ============================
    @Test
    void testCreateTask_TitleEmpty() {

        PostTaskDto request = new PostTaskDto();
        request.setTitle("");  // EMPTY

        assertThatThrownBy(() -> taskService.create(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Title không được để trống");
    }

    // ============================
    // ASSIGN SUCCESS
    // ============================
    @Test
    void assignTask_Success() {

        Task task = new Task();
        task.setTaskId(1);
        task.setStatus(TaskStatus.TODO);

        Project project = new Project();
        project.setProjectId(10);
        task.setProject(project);

        User user = new User();
        user.setUserId(5);

        Mockito.when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        Mockito.when(userRepository.findById(5)).thenReturn(Optional.of(user));
        Mockito.when(projectUserRepository.existsByProjectProjectIdAndUserUserId(10, 5)).thenReturn(true);
        Mockito.when(taskRepository.save(task)).thenReturn(task);

        TaskDTO result = taskService.assignTask(1, 5);

        assertThat(result).isNotNull();
        assertThat(task.getUser()).isEqualTo(user);

        verify(taskRepository).findById(1);
        verify(userRepository).findById(5);
        verify(projectUserRepository).existsByProjectProjectIdAndUserUserId(10, 5);
        verify(taskRepository).save(task);
    }

    // ============================
    // ASSIGN - TASK NOT FOUND
    // ============================
    @Test
    void assignTask_TaskNotFound() {

        Mockito.when(taskRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.assignTask(1, 5))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Task not found");

        verify(taskRepository).findById(1);
        verifyNoMoreInteractions(taskRepository, userRepository);
    }

    // ============================
    // ASSIGN - USER NOT FOUND
    // ============================
    @Test
    void assignTask_UserNotFound() {

        Task task = new Task();
        task.setTaskId(1);
        task.setStatus(TaskStatus.TODO);

        Mockito.when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        Mockito.when(userRepository.findById(5)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.assignTask(1, 5))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");

        verify(taskRepository).findById(1);
        verify(userRepository).findById(5);
    }

    // ============================
    // ASSIGN - TASK DONE
    // ============================
    @Test
    void assignTask_TaskIsDone() {

        Task task = new Task();
        task.setTaskId(1);
        task.setStatus(TaskStatus.DONE);

        Mockito.when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        assertThatThrownBy(() -> taskService.assignTask(1, 5))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Task đã DONE, không thể assign");

        verify(taskRepository).findById(1);
        verifyNoInteractions(userRepository);
    }

    // ============================
    // ASSIGN - USER NOT IN PROJECT
    // ============================
    @Test
    void assignTask_UserNotInProject() {

        Task task = new Task();
        task.setTaskId(1);
        task.setStatus(TaskStatus.TODO);

        Project project = new Project();
        project.setProjectId(10);
        task.setProject(project);

        User user = new User();
        user.setUserId(5);

        Mockito.when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        Mockito.when(userRepository.findById(5)).thenReturn(Optional.of(user));
        Mockito.when(projectUserRepository.existsByProjectProjectIdAndUserUserId(10, 5)).thenReturn(false);

        assertThatThrownBy(() -> taskService.assignTask(1, 5))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User không thuộc project");

        verify(taskRepository).findById(1);
        verify(userRepository).findById(5);
        verify(projectUserRepository).existsByProjectProjectIdAndUserUserId(10, 5);
    }
}