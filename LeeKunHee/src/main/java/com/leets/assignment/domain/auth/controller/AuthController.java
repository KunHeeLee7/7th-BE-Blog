package com.leets.assignment.domain.auth.controller;

import com.leets.assignment.domain.auth.dto.AuthRequestDTO;
import com.leets.assignment.domain.auth.dto.AuthResponseDTO;
import com.leets.assignment.domain.auth.service.AuthService;
import com.leets.assignment.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    @PostMapping("/auth/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AuthResponseDTO.SignupResDTO> signup(
            @Valid @RequestBody AuthRequestDTO.SignupDTO request
    ) {
        AuthResponseDTO.SignupResDTO result = authService.signup(request);
        return ApiResponse.onSuccess("AUTH201_1", "회원가입이 성공했습니다.", result);
    }

    @Override
    @PostMapping("/login")
    public ApiResponse<AuthResponseDTO.TokenResDTO> login(
            @Valid @RequestBody AuthRequestDTO.LoginDTO request
    ) {
        AuthResponseDTO.TokenResDTO result = authService.login(request);
        return ApiResponse.onSuccess("AUTH200_1", "로그인이 성공했습니다.", result);
    }

    @Override
    @PostMapping("/auth/reissue")
    public ApiResponse<AuthResponseDTO.TokenResDTO> reissue(
            @Valid @RequestBody AuthRequestDTO.ReissueDTO request
    ) {
        AuthResponseDTO.TokenResDTO result = authService.reissue(request);
        return ApiResponse.onSuccess("AUTH200_2", "토큰 재발급이 성공했습니다.", result);
    }
}
