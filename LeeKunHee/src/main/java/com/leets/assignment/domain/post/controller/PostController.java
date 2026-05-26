package com.leets.assignment.domain.post.controller;

import com.leets.assignment.domain.post.dto.req.PostRequestDTO;
import com.leets.assignment.domain.post.dto.res.PostResponseDTO;
import com.leets.assignment.domain.post.service.PostService;
import com.leets.assignment.global.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController implements PostApi{

    private final PostService postService;

    // 1. 게시글 작성
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PostResponseDTO.PostDetailResDTO> createPost(
            @Valid @RequestBody PostRequestDTO.CreatePostDTO request
    ) {
        PostResponseDTO.PostDetailResDTO result = postService.createPost(request);
        return ApiResponse.onSuccess("POST201_1", "게시글 작성에 성공했습니다.", result);
    }

    // 2. 게시글 전체 목록 조회
    @Override
    @GetMapping
    public ApiResponse<List<PostResponseDTO.PostListResDTO>> getPostList() {
        List<PostResponseDTO.PostListResDTO> result = postService.getPostList();
        return ApiResponse.onSuccess("POST200_1", "게시글 목록 조회에 성공했습니다.", result);
    }

    // 3. 게시글 상세 조회
    @Override
    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDTO.PostDetailResDTO> getPost(@PathVariable Long postId) {
        PostResponseDTO.PostDetailResDTO result = postService.getPost(postId);
        return ApiResponse.onSuccess("POST200_2", "게시글 상세 조회에 성공했습니다.", result);
    }

    // 4. 게시글 수정
    @Override
    @PatchMapping("/{postId}")
    public ApiResponse<PostResponseDTO.PostDetailResDTO> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostRequestDTO.UpdatePostDTO request
    ) {
        PostResponseDTO.PostDetailResDTO result = postService.updatePost(postId, request);
        return ApiResponse.onSuccess("POST200_3", "게시글이 수정되었습니다.", result);
    }

    // 5. 게시글 삭제
    @Override
    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(
            @PathVariable Long postId,
            @RequestParam Long userId // 쿼리 파라미터(?userId=1)로 작성자 ID를 받음
    ) {
        postService.deletePost(postId, userId);
        return ApiResponse.onSuccess("POST200_4", "게시글 삭제에 성공했습니다.", null);
    }

    // 6. 게시글 숨기기
    @Override
    @PatchMapping("/{postId}/hide")
    public ApiResponse<Void> hidePost(
            @PathVariable Long postId,
            @RequestParam Long userId // 실제로는 인증된 유저 정보를 사용해야 함
    ) {
        postService.hidePost(postId, userId);
        return ApiResponse.onSuccess("POST200_5", "게시글이 숨김 처리되었습니다.", null);
    }
}
