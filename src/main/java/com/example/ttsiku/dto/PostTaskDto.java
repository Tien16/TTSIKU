package com.example.ttsiku.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostTaskDto {

    @NotBlank(message = "Title không được để trống")
    @Size(min = 3, max = 200, message = "Title từ 3-200 ký tự")
    private String title;

    @NotBlank(message = "Description không được để trống")
    @Size(max = 500, message = "Description tối đa 500 ký tự")
    private String description;

    private String status;

    @NotNull(message = "ProjectId không được null")
    private Integer projectId;

    private Integer userId;

    @NotNull(message = "Deadline không được null")
    @Future(message = "Deadline phải lớn hơn hiện tại")
    private LocalDateTime deadline;
}