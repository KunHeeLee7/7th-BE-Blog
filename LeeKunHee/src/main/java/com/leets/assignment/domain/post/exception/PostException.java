package com.leets.assignment.domain.post.exception;

import com.leets.assignment.domain.post.exception.code.PostErrorCode;
import lombok.Getter;

// domain.post.exception.PostException
@Getter
public class PostException extends RuntimeException {
    private final PostErrorCode errorCode;

    public PostException(PostErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
