package com.leets.assignment.domain.user.exception.code;

import com.leets.assignment.global.exception.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    USER_NOT_FOUND("USER404_1", "해당 사용자가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTS("USER409_1", "이미 등록된 이메일입니다.", HttpStatus.CONFLICT),
    NICKNAME_ALREADY_EXISTS("USER409_2", "이미 등록된 닉네임입니다.", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
