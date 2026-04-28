package com.leets.assignment.domain.report.repository;

import com.leets.assignment.domain.post.entity.Post;
import com.leets.assignment.domain.report.entity.PostReport;
import com.leets.assignment.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {
    // 특정 게시글의 전체 신고 횟수를 카운트하는 메서드
    // Post 엔티티 필드명(post) + Post 엔티티 내부의 PK 필드명(PostId)
    long countByPost_PostId(Long postId);
    // 중복 신고 확인: 동일 신고자가 동일 게시글을 이미 신고했는지 체크
    boolean existsByReporter_UserIdAndPost_PostId(Long userId, Long postId);
}