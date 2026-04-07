package com.leets.assignment.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 프로젝트 전체의 컨트롤러 예외를 여기서 다 잡습니다.
public class GlobalExceptionHandler {

    // 우리가 따로 처리하지 않은 모든 일반적인 에러(RuntimeException)를 잡는 핸들러입니다.
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e) {

        // 1. 우리가 만든 응답 객체에 에러 내용을 담습니다.
        // 명세서의 공통 에러 코드(예: COMMON_500)를 사용하면 좋습니다.
        ExceptionResponse response = ExceptionResponse.of("COMMON500_1", "예기치 않은 서버 에러가 발생했습니다.");

        // 2. HTTP 상태 코드 500과 함께 응답을 보냅니다.
        return ResponseEntity
                .internalServerError()
                .body(response);
    }

    // GlobalExceptionHandler.java 내부

    // 상황 1: JSON 형식이 아예 잘못되었을 때 (HTTP 400)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .isSuccess(false)
                .code("COMMON400_1") // 명세서 코드!
                .message("잘못된 요청입니다. JSON 형식을 확인해주세요.")
                .result(null)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    // 상황 2: @Valid 검증에 실패했을 때 (HTTP 400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .isSuccess(false)
                .code("COMMON400_2")
                .message("잘못된 요청입니다. 입력값을 확인해주세요.")
                .result(null)
                .build();

        return ResponseEntity.badRequest().body(response);
    }
}
