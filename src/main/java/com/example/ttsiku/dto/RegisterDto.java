package com.example.ttsiku.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    private String username;
    private String password;
    private String email;
}