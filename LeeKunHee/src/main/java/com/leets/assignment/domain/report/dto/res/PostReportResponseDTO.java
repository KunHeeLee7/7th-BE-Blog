package com.leets.assignment.domain.report.dto.res;

import com.leets.assignment.domain.report.entity.ReportStatus;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record PostReportResponseDTO(
        Long reportId,
        Long postId,
        Long reporterId,
        String reason,
        ReportStatus status,
        LocalDateTime createdAt
) {
}