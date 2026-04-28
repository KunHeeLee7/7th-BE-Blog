package com.leets.assignment.domain.report.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostReportRequestDTO(
        @NotNull(message = "신고자 ID는 필수입니다.")
        Long reporterId,

        @NotBlank(message = "신고 사유를 입력해주세요.")
        String reason
) {
}