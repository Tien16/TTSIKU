package com.example.ttsiku.dto;

import com.example.ttsiku.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutStatusDto {
    @NotNull(message = "Status không được để trống")
    private TaskStatus status;
}
