package com.leets.assignment.domain.report.exception.code;

import com.leets.assignment.global.exception.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReportErrorCode implements BaseErrorCode {
    REPORT_DUPLICATED("REPORT409_1", "이미 신고한 게시글입니다.", HttpStatus.CONFLICT),
    REPORT_SELF_FORBIDDEN("REPORT403_1", "자신의 게시글은 신고할 수 없습니다.", HttpStatus.FORBIDDEN),
    REPORT_NOT_FOUND("REPORT404_1", "해당 신고 내역을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    REPORT_ALREADY_RESOLVED("REPORT400_1", "이미 처리가 완료된 신고입니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}