package com.leets.assignment.domain.user.repository;

import com.leets.assignment.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 기본 findById는 JpaRepository가 제공합니다.
}