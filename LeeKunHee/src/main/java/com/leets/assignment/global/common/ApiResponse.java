package com.leets.assignment.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    private final T result;

    // 성공 응답을 생성하는 정적 팩토리 메서드
    public static <T> ApiResponse<T> onSuccess(String code, String message, T result) {
        return ApiResponse.<T>builder()
                .isSuccess(true)
                .code(code)
                .message(message)
                .result(result)
                .build();
    }

}