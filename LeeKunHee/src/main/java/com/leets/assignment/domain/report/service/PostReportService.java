package com.leets.assignment.domain.report.service;

import com.leets.assignment.domain.post.entity.Post;
import com.leets.assignment.domain.post.exception.PostException;
import com.leets.assignment.domain.post.exception.code.PostErrorCode;
import com.leets.assignment.domain.post.repository.PostRepository;
import com.leets.assignment.domain.report.dto.res.PostReportResponseDTO;
import com.leets.assignment.domain.report.entity.PostReport;
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
        // 1. 엔티티 조회
        // 유저가 없는 경우는 일반 런타임 예외나 별도의 UserException을 던질 수 있습니다.
        User reporter = userRepository.findById(reporterId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        // 게시글이 없는 경우 PostErrorCode 사용
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        // 2. 권한 및 중복 체크 (Report 전용 예외 사용)
        // 본인 글 신고 불가
        if (post.getUser().getUserId().equals(reporterId)) {
            throw new ReportException(ReportErrorCode.REPORT_SELF_FORBIDDEN);
        }

        // 중복 신고 체크
        if (postReportRepository.existsByReporter_UserIdAndPost_PostId(reporterId, postId)) {
            throw new ReportException(ReportErrorCode.REPORT_DUPLICATED);
        }

        // 3. 신고 엔티티 생성 및 저장
        PostReport report = PostReport.builder()
                .reporter(reporter)
                .post(post)
                .reason(reason)
                .build();

        PostReport savedReport = postReportRepository.save(report);

        // 4. DTO 변환 및 반환
        return PostReportResponseDTO.builder()
                .reportId(savedReport.getReportId())
                .postId(savedReport.getPost().getPostId())
                .reporterId(savedReport.getReporter().getUserId())
                .reason(savedReport.getReason())
                .status(savedReport.getStatus())
                .createdAt(savedReport.getCreatedAt())
                .build();
    }
}