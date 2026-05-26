package com.leets.assignment.domain.report.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportStatus {
    PENDING("Pending"), // 대기 중
    RESOLVED("Resolved");  // 처리 완료

    private final String description;
}