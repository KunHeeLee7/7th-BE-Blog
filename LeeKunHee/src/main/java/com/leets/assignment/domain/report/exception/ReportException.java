package com.leets.assignment.domain.report.exception;

import com.leets.assignment.domain.report.exception.code.ReportErrorCode;
import lombok.Getter;

@Getter
public class ReportException extends RuntimeException {
    private final ReportErrorCode errorCode;

    public ReportException(ReportErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}