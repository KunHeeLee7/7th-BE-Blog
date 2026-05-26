package com.leets.assignment.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "post_block")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_id")
    private Long blockId;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Enumerated(EnumType.STRING)
    @Column(name = "block_type", nullable = false)
    private BlockType blockType;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    private PostBlock(Integer sequence, BlockType blockType, String content, Post post) {
        this.sequence = sequence;
        this.blockType = blockType;
        this.content = content;
        this.post = post;
    }
}

