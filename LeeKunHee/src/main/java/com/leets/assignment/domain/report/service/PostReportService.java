package com.leets.assignment.domain.report.service;

import com.leets.assignment.domain.post.entity.Post;
import com.leets.assignment.domain.post.entity.PostStatus;
import com.leets.assignment.domain.post.exception.PostException;
import com.leets.assignment.domain.post.exception.code.PostErrorCode;
import com.leets.assignment.domain.post.repository.PostRepository;
import com.leets.assignment.domain.report.dto.res.PostReportResponseDTO;
import com.leets.assignment.domain.report.entity.PostReport;
import com.leets.assignment.domain.report.entity.ReportStatus;
import com.leets.assignment.domain.report.exception.ReportException;
import com.leets.assignment.domain.report.exception.code.ReportErrorCode;
import com.leets.assignment.domain.report.repository.PostReportRepository;
import com.leets.assignment.domain.user.entity.User;
import com.leets.assignment.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostReportService {

    private final PostReportRepository postReportRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostReportResponseDTO reportPost(Long postId, Long reporterId, String reason) {
        // 1. 검증 (중복/자기 신고 체크)
        validateReport(postId, reporterId);

        // 2. 신고 저장
        PostReport report = postReportRepository.save(createReport(postId, reporterId, reason));

        // 3. [핵심] 누적 신고 횟수 확인 (3회 이상이면 자동 숨김)
        long reportCount = postReportRepository.countByPost_PostId(postId);
        if (reportCount >= 3) {
            Post post = report.getPost();
            if (post.getStatus() == PostStatus.ACTIVE) {
                post.hideByAdmin(); // 누적 신고 임계치 도달 시 자동 숨김
            }
        }

        return convertToDTO(report);
    }

    // --- 내부 헬퍼 메서드 (기존 로직을 깔끔하게 정리) ---

    private void validateReport(Long postId, Long reporterId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        if (post.getUser().getUserId().equals(reporterId)) {
            throw new ReportException(ReportErrorCode.REPORT_SELF_FORBIDDEN);
        }

        if (postReportRepository.existsByReporter_UserIdAndPost_PostId(reporterId, postId)) {
            throw new ReportException(ReportErrorCode.REPORT_DUPLICATED);
        }
    }

    private PostReport createReport(Long postId, Long reporterId, String reason) {
        User reporter = userRepository.findById(reporterId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        Post post = postRepository.findById(postId).orElseThrow();

        return PostReport.builder()
                .reporter(reporter)
                .post(post)
                .reason(reason)
                .build();
    }

    private PostReportResponseDTO convertToDTO(PostReport report) {
        return PostReportResponseDTO.builder()
                .reportId(report.getReportId())
                .postId(report.getPost().getPostId())
                .reporterId(report.getReporter().getUserId())
                .reason(report.getReason())
                .status(report.getStatus())
                .createdAt(report.getCreatedAt())
                .build();
    }

    @Transactional
    public PostReportResponseDTO resolveReport(Long reportId) {
        // 1. 신고 존재 확인
        PostReport report = postReportRepository.findById(reportId)
                .orElseThrow(() -> new ReportException(ReportErrorCode.REPORT_NOT_FOUND));

        // 2. 연결된 게시글 존재 확인
        Post post = report.getPost();
        if (post == null) {
            throw new PostException(PostErrorCode.POST_NOT_FOUND);
        }

        // 3. 상태 변경 (이미 RESOLVED인 경우 중복 처리 방지)
        if (report.getStatus() == ReportStatus.RESOLVED) {
            throw new ReportException(ReportErrorCode.REPORT_ALREADY_RESOLVED);
        }

        report.resolve();
        post.hideByAdmin(); // Post 엔티티 내 status = PostStatus.HIDDEN_BY_ADMIN

        return convertToDTO(report);

    }


}