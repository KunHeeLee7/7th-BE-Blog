package com.leets.assignment.domain.report.entity;

import com.leets.assignment.domain.user.entity.User;
import com.leets.assignment.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId; // 신고 아이디

    @Column(nullable = false)
    private String reason; // 신고 사유

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status = ReportStatus.PENDING; // 신고 상태 (기본값: 대기중)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter; // 신고자

    protected BaseReport(User reporter, String reason) {
        this.reporter = reporter;
        this.reason = reason;
        this.status = ReportStatus.PENDING;
    }

    public void resolve() {
        this.status = ReportStatus.RESOLVED;
    }
}