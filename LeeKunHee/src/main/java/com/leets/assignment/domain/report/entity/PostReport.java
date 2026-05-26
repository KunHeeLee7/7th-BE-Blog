package com.leets.assignment.domain.report.entity;

import com.leets.assignment.domain.post.entity.Post;
import com.leets.assignment.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_reports")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReport extends BaseReport {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post; // 신고 대상 게시글

    @Builder
    public PostReport(User reporter, Post post, String reason) {
        super(reporter, reason);
        this.post = post;
    }
}