package com.leets.assignment.domain.post.controller;

import com.leets.assignment.domain.post.dto.req.PostRequestDTO;
import com.leets.assignment.domain.post.dto.res.PostResponseDTO;
import com.leets.assignment.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*; // Spring Web 어노테이션 필수

import java.util.List;

@Tag(name = "01. Post API", description = "게시글 작성, 조회, 수정, 삭제, 숨김 관련 API")
public interface PostApi {

    @Operation(summary = "게시글 작성", description = "제목과 여러 블록으로 구성된 게시글을 작성합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "작성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON400", description = "입력값 검증 실패 (Validation Error)",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\"isSuccess\": false, \"code\": \"COMMON400\", \"message\": \"입력값이 유효하지 않습니다.\", \"result\": null}")))
    })
    @PostMapping("/")
    ApiResponse<PostResponseDTO.PostDetailResDTO> createPost(@RequestBody PostRequestDTO.CreatePostDTO request);

    @Operation(summary = "게시글 전체 목록 조회", description = "시스템의 모든 활성화된 게시글 목록을 조회합니다.")
    @GetMapping("/")
    ApiResponse<List<PostResponseDTO.PostListResDTO>> getPostList();

    @Operation(summary = "게시글 상세 조회", description = "특정 게시글의 모든 정보와 블록 데이터를 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST404_1", description = "해당 게시글이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\"isSuccess\": false, \"code\": \"POST404_1\", \"message\": \"해당 게시글이 존재하지 않습니다.\", \"result\": null}")))
    })
    @GetMapping("/{postId}")
    ApiResponse<PostResponseDTO.PostDetailResDTO> getPost(@PathVariable(name = "postId") Long postId);

    @Operation(summary = "게시글 수정", description = "게시글의 제목이나 블록 내용을 수정합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST403_1", description = "수정 권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\"isSuccess\": false, \"code\": \"POST403_1\", \"message\": \"해당 게시글에 대한 권한이 없습니다.\", \"result\": null}"))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST404_1", description = "수정할 게시글이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\"isSuccess\": false, \"code\": \"POST404_1\", \"message\": \"해당 게시글이 존재하지 않습니다.\", \"result\": null}")))
    })
    @PatchMapping("/{postId}")
    ApiResponse<PostResponseDTO.PostDetailResDTO> updatePost(
            @PathVariable(name = "postId") Long postId,
            @RequestBody PostRequestDTO.UpdatePostDTO request
    );

    @Operation(summary = "게시글 삭제", description = "게시글을 시스템에서 완전히 삭제합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST403_1", description = "삭제 권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\"isSuccess\": false, \"code\": \"POST403_1\", \"message\": \"해당 게시글에 대한 권한이 없습니다.\", \"result\": null}"))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST404_1", description = "삭제할 게시글이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\"isSuccess\": false, \"code\": \"POST404_1\", \"message\": \"해당 게시글이 존재하지 않습니다.\", \"result\": null}")))
    })
    @DeleteMapping("/{postId}")
    ApiResponse<Void> deletePost(
            @PathVariable(name = "postId") Long postId,
            @RequestParam(name = "userId") Long userId
    );

    @Operation(summary = "게시글 숨기기 (사용자)", description = "작성자가 자신의 게시글을 목록에서 숨깁니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "숨김 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST403_1", description = "숨김 권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\"isSuccess\": false, \"code\": \"POST403_1\", \"message\": \"해당 게시글에 대한 권한이 없습니다.\", \"result\": null}"))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST404_1", description = "숨길 게시글이 존재하지 않습니다.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\"isSuccess\": false, \"code\": \"POST404_1\", \"message\": \"해당 게시글이 존재하지 않습니다.\", \"result\": null}")))
    })
    @PatchMapping("/{postId}/hide")
    ApiResponse<Void> hidePost(
            @PathVariable(name = "postId") Long postId,
            @RequestParam(name = "userId") Long userId
    );
}