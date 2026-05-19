package com.leets.assignment.domain.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoUserInfoResponse(
        Long id,
        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
) {

    public String getProviderId() {
        return String.valueOf(id);
    }

    public String getEmailOrDefault() {
        if (kakaoAccount != null && kakaoAccount.email != null && !kakaoAccount.email.isBlank()) {
            return kakaoAccount.email;
        }
        return getProviderId() + "@kakao.local";
    }

    public String getNicknameOrDefault() {
        String nickname = null;

        if (kakaoAccount != null && kakaoAccount.profile != null) {
            nickname = kakaoAccount.profile.nickname;
        }

        if (nickname == null || nickname.isBlank()) {
            return "kakao_" + getProviderId();
        }

        return nickname;
    }

    public String getNameOrDefault() {
        return getNicknameOrDefault();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record KakaoAccount(
            String email,
            Profile profile
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Profile(
            String nickname
    ) {
    }
}
