package com.leets.assignment.domain.user.exception;

import com.leets.assignment.domain.user.exception.code.UserErrorCode;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {
    private final UserErrorCode errorCode;

    public UserException(UserErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
