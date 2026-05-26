package com.leets.assignment.domain.post.exception.code;

import com.leets.assignment.global.exception.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// domain.post.exception.PostErrorCode (Enum)
@Getter
@AllArgsConstructor
public enum PostErrorCode implements BaseErrorCode {
    POST_FORBIDDEN("POST403_1", "해당 게시글에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),
    POST_NOT_FOUND("POST404_1", "해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}