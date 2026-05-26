package com.leets.assignment.domain.report.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostReportRequestDTO(
        @Schema(description = "신고자 유저 ID", example = "2")
        @NotNull(message = "신고자 ID는 필수입니다.")
        Long reporterId,

        @Schema(description = "신고 사유", example = "스팸 및 광고성 게시글")
        @NotBlank(message = "신고 사유를 입력해주세요.")
        String reason
) {
}