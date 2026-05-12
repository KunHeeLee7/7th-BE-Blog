package com.leets.assignment.global.exception;

import com.leets.assignment.domain.post.exception.code.PostErrorCode;
import com.leets.assignment.domain.post.exception.PostException;
import com.leets.assignment.domain.auth.exception.AuthException;
import com.leets.assignment.domain.auth.exception.code.AuthErrorCode;
import com.leets.assignment.domain.report.exception.ReportException;
import com.leets.assignment.domain.report.exception.code.ReportErrorCode;
import com.leets.assignment.domain.user.exception.UserException;
import com.leets.assignment.domain.user.exception.code.UserErrorCode;
import com.leets.assignment.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
        // 1. DTO에 적힌 메시지 추출
        FieldError fieldError = e.getBindingResult().getFieldError();
        String rawMessage = e.getBindingResult().getFieldError().getDefaultMessage();

        String code = "COMMON400";
        String message = rawMessage;

        // 2. 구분자가 있다면 쪼개기
        if (rawMessage != null && rawMessage.contains("|")) {
            String[] parts = rawMessage.split("\\|");
            code = parts[0];
            message = parts[1];
        }

        return ResponseEntity.badRequest().body(
                ApiResponse.<Void>builder()
                        .isSuccess(false)
                        .code(code)
                        .message(message)
                        .build()
        );
    }

    /**
     * Post 관련 모든 커스텀 예외 처리
     */
    @ExceptionHandler(PostException.class)
    public ResponseEntity<ApiResponse<Void>> handlePostException(PostException e) {
        PostErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(
                ApiResponse.<Void>builder()
                        .isSuccess(false)
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserException(UserException e) {
        UserErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(
                ApiResponse.<Void>builder()
                        .isSuccess(false)
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthException(AuthException e) {
        AuthErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(
                ApiResponse.<Void>builder()
                        .isSuccess(false)
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
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
     * [REPORT400] ReportException
     */
    @ExceptionHandler(ReportException.class)
    public ResponseEntity<ApiResponse<Void>> handleReportException(ReportException e) {
        ReportErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(
                ApiResponse.<Void>builder()
                        .isSuccess(false)
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
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
