package com.leets.assignment.domain.post.repository;

import com.leets.assignment.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 기본적으로 save(), findById(), delete() 등을 제공.
}