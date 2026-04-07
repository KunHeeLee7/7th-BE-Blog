package com.leets.assignment.global.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExceptionResponse {
    private final boolean isSuccess;
    private final String code;
    private final String message;
    private final Object result;

    // 에러 발생 시 응답을 생성하는 정적 메서드
    public static ExceptionResponse of(String code, String message) {
        return ExceptionResponse.builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .result(null)
                .build();
    }
}