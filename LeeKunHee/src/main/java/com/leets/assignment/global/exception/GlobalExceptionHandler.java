package com.leets.assignment.global.exception;

import com.leets.assignment.domain.post.exception.PostNotFoundException;
import com.leets.assignment.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * [POST400_1, POST400_2] @Valid 필드 검증 실패 처리
     * 명세서의 특정 에러 코드와 메시지 형식에 맞춥니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
        // 발생한 에러 중 첫 번째 필드 에러를 가져옴
        FieldError fieldError = e.getBindingResult().getFieldError();

        String errorCode = "COMMON400"; // 기본 에러 코드
        String errorMessage = "입력값이 유효하지 않습니다.";

        if (fieldError != null) {
            String constraint = fieldError.getCode(); // NotBlank, Size 등
            String field = fieldError.getField();     // title, blocks 등

            // 1. 제목이나 내용(블록)이 비었을 때 (POST400_1)
            if ("NotBlank".equals(constraint) || "NotEmpty".equals(constraint)) {
                errorCode = "POST400_1";
                errorMessage = "제목과 내용을 입력해주세요.";
            }
            // 2. 제목 글자 수 초과 시 (POST400_2)
            else if ("Size".equals(constraint) && "title".equals(field)) {
                errorCode = "POST400_2";
                errorMessage = "제목은 최대 255자까지 가능합니다.";
            }
        }

        return ResponseEntity.badRequest().body(
                ApiResponse.<Void>builder()
                        .isSuccess(false)
                        .code(errorCode)
                        .message(errorMessage)
                        .result(null)
                        .build()
        );
    }

    /**
     * [COMMON400_1] JSON 형식 자체 오류
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(
                ApiResponse.<Void>builder()
                        .isSuccess(false)
                        .code("COMMON400_1")
                        .message("잘못된 요청입니다. JSON 형식을 확인해주세요.")
                        .result(null)
                        .build()
        );
    }

    /**
     * [POST404_1] 게시글을 찾을 수 없음 (상세조회, 수정, 삭제 공통)
     */
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handlePostNotFoundException(PostNotFoundException e) {
        return ResponseEntity.status(404).body(
                ApiResponse.<Void>builder()
                        .isSuccess(false)
                        .code("POST404_1")
                        .message("해당 게시글이 존재하지 않습니다.")
                        .result(null)
                        .build()
        );
    }

    /**
     * [COMMON500_1] 기타 예기치 못한 서버 에러
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAllException(Exception e) {
        return ResponseEntity.internalServerError().body(
                ApiResponse.<Void>builder()
                        .isSuccess(false)
                        .code("COMMON500_1")
                        .message("예기치 않은 서버 에러가 발생했습니다.")
                        .result(null)
                        .build()
        );
    }
}