package com.leets.assignment.domain.post.controller;

import com.leets.assignment.domain.post.dto.req.PostRequestDTO;
import com.leets.assignment.domain.post.dto.res.PostResponseDTO;
import com.leets.assignment.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostResponseDTO.PostDetailResDTO createPost(
            @Valid @RequestBody PostRequestDTO.CreatePostDTO request
    ) {
        // 우선 ApiResponse 없이 DTO만 반환해서 잘 나오는지 확인해봅시다!
        return postService.createPost(request);
    }
}