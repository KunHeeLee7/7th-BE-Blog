package com.leets.assignment.domain.post.controller;

import com.leets.assignment.domain.post.dto.req.PostRequestDTO;
import com.leets.assignment.domain.post.dto.res.PostResponseDTO;
import com.leets.assignment.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping
    public PostResponseDTO.PostDetailResDTO createPost(
            @Valid @RequestBody PostRequestDTO.CreatePostDTO request
    ) {
        return postService.createPost(request);
    }

    // 게시글 상세 조회 (ID 필요)
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO.PostDetailResDTO> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    // 게시글 전체 목록 조회 (추가됨)
    @GetMapping
    public ResponseEntity<List<PostResponseDTO.PostListResDTO>> getPostList() {
        return ResponseEntity.ok(postService.getPostList());
    }
}