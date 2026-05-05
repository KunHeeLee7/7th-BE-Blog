package com.leets.assignment.domain.post.dto.res;

import com.leets.assignment.domain.post.entity.BlockType;
import com.leets.assignment.domain.post.entity.Post;
import com.leets.assignment.domain.post.entity.PostBlock;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDTO {

    // 1. 게시글 목록 조회용 (나중에 목록 기능 만들 때 사용)
    @Builder
    public record PostListResDTO (
            @Schema(description = "게시글 ID", example = "1")
            Long postId,
            @Schema(description = "게시글 제목", example = "첫 번째 게시글")
            String title,
            @Schema(description = "작성자 닉네임", example = "길동이")
            String nickname,
            @Schema(description = "작성 일시")
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
            @Schema(description = "게시글 ID", example = "1")
            Long postId,
            @Schema(description = "게시글 제목", example = "상세 조회 제목")
            String title,
            @Schema(description = "작성자 닉네임", example = "길동이")
            String nickname,
            @Schema(description = "블록 상세 정보 리스트")
            List<BlockResDTO> blocks,
            @Schema(description = "작성 일시")
            LocalDateTime createdAt,
            @Schema(description = "수정 일시")
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
            @Schema(description = "블록 식별자 ID", example = "100")
            Long blockId,
            @Schema(description = "블록 순서", example = "0")
            Integer sequence,
            @Schema(description = "블록 타입", example = "IMAGE")
            BlockType blockType,
            @Schema(description = "블록 내용", example = "https://example.com/image.png")
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