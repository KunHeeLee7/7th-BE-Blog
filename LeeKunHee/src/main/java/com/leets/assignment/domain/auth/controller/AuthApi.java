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
}
