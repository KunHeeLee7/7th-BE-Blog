package com.leets.assignment.domain.auth.service;

import com.leets.assignment.domain.auth.dto.AuthRequestDTO;
import com.leets.assignment.domain.auth.dto.AuthResponseDTO;
import com.leets.assignment.domain.auth.exception.AuthException;
import com.leets.assignment.domain.auth.exception.code.AuthErrorCode;
import com.leets.assignment.domain.auth.jwt.JwtProvider;
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
    private final JwtProvider jwtProvider;

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

    public AuthResponseDTO.TokenResDTO login(AuthRequestDTO.LoginDTO request) {
        User user = userService.findByEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException(AuthErrorCode.INVALID_PASSWORD);
        }

        return jwtProvider.createTokenResponse(user);
    }

    public AuthResponseDTO.TokenResDTO reissue(AuthRequestDTO.ReissueDTO request) {
        jwtProvider.validateRefreshToken(request.getRefreshToken());

        String email = jwtProvider.getEmail(request.getRefreshToken());
        User user = userService.findByEmail(email);

        return jwtProvider.createTokenResponse(user);
    }
}
