package com.example.ttsiku.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Map<String, Object> handleRuntime(RuntimeException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("status", 400);
        return error;
    }
}