package com.leets.assignment.domain.post.dto.res;

import com.leets.assignment.domain.post.entity.BlockType;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDTO {

    // 게시글 목록 조회
    @Builder
    public record PostListResDTO (
            Long postId,
            String title,
            String nickname,
            LocalDateTime createdAt
    ){}

    // 게시글 상세 조회
    @Builder
    public record PostDetailResDTO (
            Long postId,
            String title,
            String nickname,
            List<BlockResDTO> blocks, // 블록 리스트
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){}

    @Builder
    public record BlockResDTO (
            Long blockId,
            Integer sequence,
            BlockType blockType,
            String content
    ){}
}