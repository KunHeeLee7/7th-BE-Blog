package com.leets.assignment.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public class AuthResponseDTO {

    @Builder
    public record SignupResDTO(
            @Schema(description = "사용자 ID", example = "1")
            Long userId,
            @Schema(description = "이메일", example = "test@example.com")
            String email,
            @Schema(description = "닉네임", example = "leets")
            String nickname
    ) {
    }

    @Builder
    public record TokenResDTO(
            @Schema(description = "토큰 타입", example = "Bearer")
            String tokenType,
            @Schema(description = "Access Token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwidXNlcklkIjoxLCJuaWNrbmFtZSI6ImxlZXRzIiwiY2F0ZWdvcnkiOiJhY2Nlc3MiLCJpYXQiOjE3MTYxOTAwMDAsImV4cCI6MTcxNjE5MzYwMH0.sample-signature")
            String accessToken,
            @Schema(description = "Refresh Token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwidXNlcklkIjoxLCJuaWNrbmFtZSI6ImxlZXRzIiwiY2F0ZWdvcnkiOiJyZWZyZXNoIiwiaWF0IjoxNzE2MTkwMDAwLCJleHAiOjE3MTczOTk2MDB9.sample-signature")
            String refreshToken
    ) {
    }
}
