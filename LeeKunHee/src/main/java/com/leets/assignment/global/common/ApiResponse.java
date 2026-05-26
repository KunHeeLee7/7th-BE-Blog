package com.leets.assignment.global.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Schema(description = "공통 응답 규격")
public class ApiResponse<T> {
    @Schema(description = "성공 여부", example = "true")
    private final Boolean isSuccess;

    @Schema(description = "응답 코드", example = "COMMON200")
    private final String code;

    @Schema(description = "응답 메시지", example = "요청에 성공하였습니다.")
    private final String message;

    @Schema(description = "실제 응답 데이터") // 추가
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