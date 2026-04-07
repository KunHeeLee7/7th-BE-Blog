package com.leets.assignment.domain.post.controller;

import com.leets.assignment.domain.post.dto.req.PostRequestDTO;
import com.leets.assignment.domain.post.dto.res.PostResponseDTO;
import com.leets.assignment.domain.post.service.PostService;
import com.leets.assignment.global.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 1. 게시글 작성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PostResponseDTO.PostDetailResDTO> createPost(
            @Valid @RequestBody PostRequestDTO.CreatePostDTO request
    ) {
        PostResponseDTO.PostDetailResDTO result = postService.createPost(request);
        return ApiResponse.onSuccess("POST201_1", "게시글 작성에 성공했습니다.", result);
    }

    // 2. 게시글 전체 목록 조회
    @GetMapping
    public ApiResponse<List<PostResponseDTO.PostListResDTO>> getPostList() {
        List<PostResponseDTO.PostListResDTO> result = postService.getPostList();
        return ApiResponse.onSuccess("POST200_1", "게시글 목록 조회에 성공했습니다.", result);
    }

    // 3. 게시글 상세 조회
    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDTO.PostDetailResDTO> getPost(@PathVariable Long postId) {
        PostResponseDTO.PostDetailResDTO result = postService.getPost(postId);
        return ApiResponse.onSuccess("POST200_2", "게시글 상세 조회에 성공했습니다.", result);
    }

    // 4. 게시글 수정
    @PatchMapping("/{postId}")
    public ApiResponse<PostResponseDTO.PostDetailResDTO> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostRequestDTO.UpdatePostDTO request
    ) {
        PostResponseDTO.PostDetailResDTO result = postService.updatePost(postId, request);
        return ApiResponse.onSuccess("POST200_3", "게시글이 수정되었습니다.", result);
    }

    // 5. 게시글 삭제
    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(
            @PathVariable Long postId,
            @RequestParam Long userId // 쿼리 파라미터(?userId=1)로 작성자 ID를 받음
    ) {
        postService.deletePost(postId, userId);
        // 삭제는 반환할 데이터가 없으므로 result에 null을 넣습니다.
        return ApiResponse.onSuccess("POST200_4", "게시글 삭제에 성공했습니다.", null);
    }

}