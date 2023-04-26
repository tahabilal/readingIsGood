package com.example.readingisgood.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Success {
    private final Object response;
    private final LocalDateTime timestamp;
    private final int statusCode;
    private final String message;

    public Success(Object response, String message) {
        this.response = response;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.statusCode = 200;
    }
}
