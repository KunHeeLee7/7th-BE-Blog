package com.leets.assignment.domain.auth.exception.code;

import com.leets.assignment.global.exception.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {
    INVALID_PASSWORD("AUTH401_1", "비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("AUTH401_2", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("AUTH401_3", "만료된 토큰입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN("AUTH401_4", "유효하지 않은 Refresh Token입니다.", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
