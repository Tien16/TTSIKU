package com.example.ttsiku.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends AppException {
    public InternalServerException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", message);
    }
}