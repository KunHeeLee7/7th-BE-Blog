package com.leets.assignment.domain.post.service;

import com.leets.assignment.domain.post.dto.req.PostRequestDTO;
import com.leets.assignment.domain.post.dto.res.PostResponseDTO;
import com.leets.assignment.domain.post.entity.Post;
import com.leets.assignment.domain.post.entity.PostBlock;
import com.leets.assignment.domain.post.exception.PostNotFoundException;
import com.leets.assignment.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 기본적으로 읽기 전용으로 설정 (성능 최적화)
public class PostService {

    private final PostRepository postRepository;

    // 게시글 생성
    @Transactional // 쓰기 작업이므로 readOnly = false (기본값) 적용
    public PostResponseDTO.PostDetailResDTO createPost(PostRequestDTO.CreatePostDTO request) {
        // 1. Post 엔티티 생성
        Post post = Post.builder()
                .title(request.getTitle())
                .build();

        // 2. 블록 추가 로직
        request.getBlocks().forEach(blockDto -> {
            PostBlock block = PostBlock.builder()
                    .sequence(blockDto.getSequence())
                    .blockType(blockDto.getBlockType())
                    .content(blockDto.getContent())
                    .post(post)
                    .build();
            post.getBlocks().add(block);
        });

        // 3. DB 저장
        Post savedPost = postRepository.save(post);

        // 4. 저장된 엔티티를 DTO로 변환하여 반환 (미리 만든 from 메서드 활용)
        return PostResponseDTO.PostDetailResDTO.from(savedPost);
    }

    // 게시글 상세 조회
    public PostResponseDTO.PostDetailResDTO getPost(Long postId) {
        // 1. DB에서 ID로 조회
        // 2. 데이터가 없으면 우리가 만든 PostNotFoundException 예외 발생! (-> 404 응답)
        Post post = postRepository.findById(postId)
                .filter(p -> p.getDeletedAt() == null) // 삭제 안 된 것만 필터링
                .orElseThrow(PostNotFoundException::new);

        // 3. 찾은 엔티티를 DTO로 변환하여 반환
        return PostResponseDTO.PostDetailResDTO.from(post);
    }

    // 게시글 전체 목록 조회
    public List<PostResponseDTO.PostListResDTO> getPostList() {
        // DB의 모든 글을 가져와서 ListResDTO로 변환
        return postRepository.findAll().stream()
                .filter(post -> post.getDeletedAt() == null) // 삭제된 글 제외
                .map(PostResponseDTO.PostListResDTO::from)
                .collect(Collectors.toList());
    }

    // 소프트 딜리트 로직
    @Transactional
    public void deletePost(Long postId) {
        // 1. 존재하는 글인지 확인
        Post post = postRepository.findById(postId)
                .filter(p -> p.getDeletedAt() == null) // 이미 삭제된 건 없는 걸로 침
                .orElseThrow(PostNotFoundException::new);

        // 2. setDeletedAt 대신 softDelete() 호출!
        post.softDelete();
    }
}