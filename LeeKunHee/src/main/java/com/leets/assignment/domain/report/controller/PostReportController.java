package com.leets.assignment.domain.report.controller;

import com.leets.assignment.domain.report.dto.req.PostReportRequestDTO;
import com.leets.assignment.domain.report.dto.res.PostReportResponseDTO;
import com.leets.assignment.domain.report.service.PostReportService;
import com.leets.assignment.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostReportController {

    private final PostReportService postReportService;

    @PostMapping("/{postId}/reports")
    public ApiResponse<PostReportResponseDTO> createReport(
            @PathVariable Long postId,
            @Valid @RequestBody PostReportRequestDTO request) {

        PostReportResponseDTO response = postReportService.reportPost(postId, request.reporterId(), request.reason());
        return ApiResponse.onSuccess("REPORT201", "신고가 정상적으로 접수되었습니다.", response);
    }

    @PatchMapping("/reports/{reportId}/resolve")
    public ApiResponse<PostReportResponseDTO> resolveReport(
            @PathVariable Long reportId
    ) {
        PostReportResponseDTO response = postReportService.resolveReport(reportId);
        return ApiResponse.onSuccess("REPORT200_1", "신고 처리가 완료되어 해당 게시글이 숨겨졌습니다.", response);
    }
}