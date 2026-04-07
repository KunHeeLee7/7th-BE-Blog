package com.leets.assignment.domain.post.entity;

import com.leets.assignment.global.baseEntity.BaseEntity;
import com.leets.assignment.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // FK 컬럼명 명시
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostBlock> blocks = new ArrayList<>();

    @Builder
    private Post(String title, User user) {
        this.title = title;
        this.user = user;
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void update(String title) {
        this.title = title;
    }
}