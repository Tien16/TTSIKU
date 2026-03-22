package com.example.ttsiku.entity;

import com.example.ttsiku.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;

    @NotBlank(message = "Title không được để trống")
    @Size(min = 3, max = 200, message = "Title phải từ 3 đến 200 ký tự")
    private String title;

    @NotBlank(message = "Description không được để trống")
    @Size(max = 500, message = "Description tối đa 500 ký tự")
    private String description;

    @NotNull(message = "Status không được null")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @NotNull(message = "Deadline không được null")
    @Future(message = "Deadline phải lớn hơn thời điểm hiện tại")
    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    private Project project;
}