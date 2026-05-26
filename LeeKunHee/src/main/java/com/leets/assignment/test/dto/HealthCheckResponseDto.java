package com.leets.assignment.test.dto;

public class HealthCheckResponseDto {
    private final String message;

    public HealthCheckResponseDto(String message) {
        this.message = message;
    }

    // JSON으로 변환될 때 필요한 Getter
    public String getMessage() {
        return message;
    }
}
