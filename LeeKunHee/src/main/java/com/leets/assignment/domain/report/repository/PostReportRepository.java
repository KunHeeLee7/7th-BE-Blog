package com.leets.assignment.domain.report.repository;

import com.leets.assignment.domain.post.entity.Post;
import com.leets.assignment.domain.report.entity.PostReport;
import com.leets.assignment.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {
    // 중복 신고 확인: 동일 신고자가 동일 게시글을 이미 신고했는지 체크
    boolean existsByReporter_UserIdAndPost_PostId(Long userId, Long postId);
}