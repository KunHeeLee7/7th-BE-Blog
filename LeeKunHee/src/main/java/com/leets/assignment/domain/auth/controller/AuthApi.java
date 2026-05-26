package com.leets.assignment.domain.auth.controller;

import com.leets.assignment.domain.auth.dto.AuthRequestDTO;
import com.leets.assignment.domain.auth.dto.AuthResponseDTO;
import com.leets.assignment.global.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "03. Auth API", description = "회원가입 및 로그인 API")
public interface AuthApi {

    @Operation(summary = "회원가입", description = "이메일, 닉네임, 이름, 비밀번호로 회원가입을 진행합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER409_1", description = "이미 등록된 이메일",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\"isSuccess\": false, \"code\": \"USER409_1\", \"message\": \"이미 등록된 이메일입니다.\", \"result\": null}"))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER409_2", description = "이미 등록된 닉네임",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\"isSuccess\": false, \"code\": \"USER409_2\", \"message\": \"이미 등록된 닉네임입니다.\", \"result\": null}")))
    })
    ApiResponse<AuthResponseDTO.SignupResDTO> signup(@RequestBody AuthRequestDTO.SignupDTO request);

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하고 Access Token과 Refresh Token을 발급받습니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH401_1", description = "비밀번호 불일치",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\"isSuccess\": false, \"code\": \"AUTH401_1\", \"message\": \"비밀번호가 일치하지 않습니다.\", \"result\": null}")))
    })
    ApiResponse<AuthResponseDTO.TokenResDTO> login(@RequestBody AuthRequestDTO.LoginDTO request);

    @Operation(summary = "토큰 재발급", description = "Refresh Token으로 새로운 Access Token과 Refresh Token을 발급받습니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH401_4", description = "유효하지 않은 Refresh Token",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(value = "{\"isSuccess\": false, \"code\": \"AUTH401_4\", \"message\": \"유효하지 않은 Refresh Token입니다.\", \"result\": null}")))
    })
    ApiResponse<AuthResponseDTO.TokenResDTO> reissue(@RequestBody AuthRequestDTO.ReissueDTO request);

    @Operation(summary = "카카오 로그인", description = "카카오 인가 코드를 받아 서비스 Access Token과 Refresh Token을 발급합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "카카오 로그인 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH401_2", description = "유효하지 않은 카카오 인가 코드 또는 토큰")
    })
    ApiResponse<AuthResponseDTO.TokenResDTO> kakaoLogin(@RequestParam String code);
}
