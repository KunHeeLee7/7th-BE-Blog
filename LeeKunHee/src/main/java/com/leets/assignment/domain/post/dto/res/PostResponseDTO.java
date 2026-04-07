package com.leets.assignment.domain.post.dto.res;

import com.leets.assignment.domain.post.entity.BlockType;
import com.leets.assignment.domain.post.entity.Post;
import com.leets.assignment.domain.post.entity.PostBlock;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDTO {

    // 1. 게시글 목록 조회용 (나중에 목록 기능 만들 때 사용)
    @Builder
    public record PostListResDTO (
            Long postId,
            String title,
            String nickname,
            LocalDateTime createdAt
    ){
        public static PostListResDTO from(Post post) {
            return PostListResDTO.builder()
                    .postId(post.getPostId())
                    .title(post.getTitle())
                    .nickname(post.getUser() != null ? post.getUser().getNickname() : "알 수 없음")
                    .createdAt(post.getCreatedAt())
                    .build();
        }
    }

    // 2. 게시글 상세 조회용 (현재 구현 중인 기능)
    @Builder
    public record PostDetailResDTO (
            Long postId,
            String title,
            String nickname,
            List<BlockResDTO> blocks,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        // 이 메서드가 있어야 Service에서 .from(post)를 호출할 수 있습니다!
        public static PostDetailResDTO from(Post post) {
            return PostDetailResDTO.builder()
                    .postId(post.getPostId())
                    .title(post.getTitle())
                    .nickname(post.getUser() != null ? post.getUser().getNickname() : "알 수 없음")
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .blocks(post.getBlocks().stream()
                            .map(BlockResDTO::from) // 아래 BlockResDTO.from 호출
                            .toList())
                    .build();
        }
    }

    // 3. 블록 상세 정보
    @Builder
    public record BlockResDTO (
            Long blockId,
            Integer sequence,
            BlockType blockType,
            String content
    ){
        public static BlockResDTO from(PostBlock block) {
            return BlockResDTO.builder()
                    .blockId(block.getBlockId())
                    .sequence(block.getSequence())
                    .blockType(block.getBlockType())
                    .content(block.getContent())
                    .build();
        }
    }
}