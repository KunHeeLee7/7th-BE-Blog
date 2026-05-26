package com.leets.assignment.domain.report.controller;

import com.leets.assignment.domain.report.dto.req.PostReportRequestDTO;
import com.leets.assignment.domain.report.dto.res.PostReportResponseDTO;
import com.leets.assignment.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "02. Report API", description = "신고 접수 및 처리 API")
public interface PostReportApi {

    @Operation(summary = "게시글 신고 접수", description = "부적절한 게시글을 신고합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "접수 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REPORT409_1", description = "이미 신고한 게시글입니다.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\"isSuccess\": false, \"code\": \"REPORT409_1\", \"message\": \"이미 신고한 게시글입니다.\", \"result\": null}"))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REPORT403_1", description = "자신의 게시글은 신고할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\"isSuccess\": false, \"code\": \"REPORT403_1\", \"message\": \"자신의 게시글은 신고할 수 없습니다.\", \"result\": null}")))
    })
    ApiResponse<PostReportResponseDTO> createReport(Long postId, PostReportRequestDTO request);

    @Operation(summary = "신고 처리 (관리자)", description = "신고를 처리하고 게시글을 숨깁니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "처리 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REPORT404_1", description = "해당 신고 내역을 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\"isSuccess\": false, \"code\": \"REPORT404_1\", \"message\": \"해당 신고 내역을 찾을 수 없습니다.\", \"result\": null}")))
    })
    ApiResponse<PostReportResponseDTO> resolveReport(Long reportId);
}