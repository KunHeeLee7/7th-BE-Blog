package com.leets.assignment.domain.user.entity;

import com.leets.assignment.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // ERD의 아이디(user_id)와 매핑
    private Long userId;

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", length = 20)
    private AuthProvider provider;

    @Column(name = "provider_id", unique = true)
    private String providerId;

    @Builder
    private User(String nickname, String name, String password, String email, AuthProvider provider, String providerId) {
        this.nickname = nickname;
        this.name = name;
        this.password = password;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
    }
}
