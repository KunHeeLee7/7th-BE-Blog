package com.leets.assignment.domain.post.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostStatus {
    ACTIVE("Active"),
    HIDDEN_BY_USER("Hidden by user"),
    HIDDEN_BY_ADMIN("Hidden by admin");

    private final String description;
}