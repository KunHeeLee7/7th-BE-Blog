package com.leets.assignment.domain.auth.service;

import com.leets.assignment.domain.auth.dto.AuthRequestDTO;
import com.leets.assignment.domain.auth.dto.AuthResponseDTO;
import com.leets.assignment.domain.user.entity.User;
import com.leets.assignment.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponseDTO.SignupResDTO signup(AuthRequestDTO.SignupDTO request) {
        userService.validateEmailNotDuplicated(request.getEmail());
        userService.validateNicknameNotDuplicated(request.getNickname());

        User user = User.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userService.save(user);

        return AuthResponseDTO.SignupResDTO.builder()
                .userId(savedUser.getUserId())
                .email(savedUser.getEmail())
                .nickname(savedUser.getNickname())
                .build();
    }
}
