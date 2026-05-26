package com.leets.assignment.domain.auth.exception;

import com.leets.assignment.domain.auth.exception.code.AuthErrorCode;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {
    private final AuthErrorCode errorCode;

    public AuthException(AuthErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
