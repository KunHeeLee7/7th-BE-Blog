package com.leets.assignment.domain.post.exception;

public class PostForbiddenException extends RuntimeException {
    public PostForbiddenException() {
        super("수정 권한이 없습니다.");
    }
}