package com.leets.assignment.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthRequestDTO {

    @Getter
    @NoArgsConstructor
    public static class SignupDTO {
        @Schema(description = "이메일", example = "test@example.com")
        @NotBlank(message = "AUTH400_1|이메일을 입력해주세요.")
        @Email(message = "AUTH400_2|올바른 이메일 형식이 아닙니다.")
        private String email;

        @Schema(description = "닉네임", example = "leets")
        @NotBlank(message = "AUTH400_3|닉네임을 입력해주세요.")
        @Size(max = 50, message = "AUTH400_4|닉네임은 최대 50자까지 가능합니다.")
        private String nickname;

        @Schema(description = "이름", example = "홍길동")
        @NotBlank(message = "AUTH400_5|이름을 입력해주세요.")
        @Size(max = 50, message = "AUTH400_6|이름은 최대 50자까지 가능합니다.")
        private String name;

        @Schema(description = "비밀번호", example = "password1234")
        @NotBlank(message = "AUTH400_7|비밀번호를 입력해주세요.")
        @Size(min = 8, max = 100, message = "AUTH400_8|비밀번호는 8자 이상 100자 이하로 입력해주세요.")
        private String password;
    }

    @Getter
    @NoArgsConstructor
    public static class LoginDTO {
        @Schema(description = "이메일", example = "test@example.com")
        @NotBlank(message = "AUTH400_1|이메일을 입력해주세요.")
        @Email(message = "AUTH400_2|올바른 이메일 형식이 아닙니다.")
        private String email;

        @Schema(description = "비밀번호", example = "password1234")
        @NotBlank(message = "AUTH400_7|비밀번호를 입력해주세요.")
        private String password;
    }

    @Getter
    @NoArgsConstructor
    public static class ReissueDTO {
        @Schema(description = "Refresh Token")
        @NotBlank(message = "AUTH400_9|Refresh Token을 입력해주세요.")
        private String refreshToken;
    }
}
