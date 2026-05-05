package com.leets.assignment.domain.report.dto.res;

import com.leets.assignment.domain.report.entity.ReportStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record PostReportResponseDTO(
        @Schema(description = "신고 식별자 ID", example = "1")
        Long reportId,
        @Schema(description = "신고된 게시글 ID", example = "10")
        Long postId,
        @Schema(description = "신고자 유저 ID", example = "2")
        Long reporterId,
        @Schema(description = "신고 사유", example = "부적절한 홍보 게시글입니다.")
        String reason,
        @Schema(description = "신고 처리 상태", example = "PENDING")
        ReportStatus status,
        @Schema(description = "신고 접수 시간")
        LocalDateTime createdAt
) {
}