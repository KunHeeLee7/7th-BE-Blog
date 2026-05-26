package com.leets.assignment.domain.auth.oauth.client;

import com.leets.assignment.domain.auth.exception.AuthException;
import com.leets.assignment.domain.auth.exception.code.AuthErrorCode;
import com.leets.assignment.domain.auth.oauth.dto.KakaoTokenResponse;
import com.leets.assignment.domain.auth.oauth.dto.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoClient {

    private static final String AUTHORIZATION_CODE = "authorization_code";
    private static final String BEARER_PREFIX = "Bearer ";

    private final RestClient restClient = RestClient.create();

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    public KakaoUserInfoResponse getUserInfo(String code) {
        KakaoTokenResponse tokenResponse = requestToken(code);
        return requestUserInfo(tokenResponse.accessToken());
    }

    private KakaoTokenResponse requestToken(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", AUTHORIZATION_CODE);
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        try {
            KakaoTokenResponse response = restClient.post()
                    .uri("https://kauth.kakao.com/oauth/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(body)
                    .retrieve()
                    .body(KakaoTokenResponse.class);
            if (response == null || response.accessToken() == null || response.accessToken().isBlank()) {
                log.warn("Kakao token response does not contain access token.");
                throw new AuthException(AuthErrorCode.INVALID_TOKEN);
            }
            return response;
        } catch (RestClientException e) {
            log.warn("Kakao token request failed. redirectUri={}, message={}", redirectUri, e.getMessage());
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
    }

    private KakaoUserInfoResponse requestUserInfo(String accessToken) {
        try {
            KakaoUserInfoResponse response = restClient.get()
                    .uri("https://kapi.kakao.com/v2/user/me")
                    .header("Authorization", BEARER_PREFIX + accessToken)
                    .retrieve()
                    .body(KakaoUserInfoResponse.class);
            if (response == null || response.id() == null) {
                log.warn("Kakao user info response does not contain user id.");
                throw new AuthException(AuthErrorCode.INVALID_TOKEN);
            }
            return response;
        } catch (RestClientException e) {
            log.warn("Kakao user info request failed. message={}", e.getMessage());
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
    }
}
