package com.leets.assignment.global.baseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    @Schema(description = "생성 일시", type = "string", example = "2024-05-20T15:30:00")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @Schema(description = "수정 일시", type = "string", example = "2024-05-21T10:00:00")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    @Schema(description = "삭제 일시 (소프트 딜리트용)", type = "string", example = "null")
    protected LocalDateTime deletedAt;
}