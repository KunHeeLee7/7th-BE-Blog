package com.leets.assignment.domain.post.service;

import com.leets.assignment.domain.post.dto.req.PostRequestDTO;
import com.leets.assignment.domain.post.dto.res.PostResponseDTO;
import com.leets.assignment.domain.post.entity.Post;
import com.leets.assignment.domain.post.entity.PostBlock;
import com.leets.assignment.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponseDTO.PostDetailResDTO createPost(PostRequestDTO.CreatePostDTO request) {
        // 1. Post 엔티티 생성
        Post post = Post.builder()
                .title(request.getTitle())
                .build();

        // 2. DTO의 블록들을 엔티티로 변환하여 Post에 추가
        request.getBlocks().forEach(blockDto -> {
            PostBlock block = PostBlock.builder()
                    .sequence(blockDto.getSequence())
                    .blockType(blockDto.getBlockType())
                    .content(blockDto.getContent())
                    .post(post)
                    .build();
            post.getBlocks().add(block);
        });

        // 3. DB 저장 (CascadeType.ALL 설정으로 블록도 같이 저장)
        Post savedPost = postRepository.save(post);

        // 4. 저장된 엔티티를 응답 DTO로 변환해서 반환
        return PostResponseDTO.PostDetailResDTO.builder()
                .postId(savedPost.getPostId())
                .title(savedPost.getTitle())
                .nickname("가천대가나디") // 우선 고정값으로 테스트
                .blocks(savedPost.getBlocks().stream()
                        .map(b -> PostResponseDTO.BlockResDTO.builder()
                                .blockId(b.getBlockId())
                                .sequence(b.getSequence())
                                .blockType(b.getBlockType())
                                .content(b.getContent())
                                .build())
                        .collect(Collectors.toList()))
                .createdAt(savedPost.getCreatedAt())
                .updatedAt(savedPost.getUpdatedAt())
                .build();
    }
}