package com.leets.assignment.domain.comment.entity;

import com.leets.assignment.global.baseEntity.BaseEntity;
import com.leets.assignment.domain.user.entity.User;
import com.leets.assignment.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder // 빌더 패턴 사용
    private Comment(String content, Post post, User user) {
        this.content = content;
        this.post = post;
        this.user = user;
    }

}