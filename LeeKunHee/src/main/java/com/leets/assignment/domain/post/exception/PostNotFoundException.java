package com.leets.assignment.domain.post.exception;

// RuntimeException을 상속받아야 서비스에서 쉽게 던질 수 있습니다.
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super("해당 게시글을 찾을 수 없습니다.");
    }
}