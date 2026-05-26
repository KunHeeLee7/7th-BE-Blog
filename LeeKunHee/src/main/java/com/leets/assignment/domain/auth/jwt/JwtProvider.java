package com.leets.assignment.domain.auth.jwt;

import com.leets.assignment.domain.auth.dto.AuthResponseDTO;
import com.leets.assignment.domain.auth.exception.AuthException;
import com.leets.assignment.domain.auth.exception.code.AuthErrorCode;
import com.leets.assignment.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    private static final String TOKEN_TYPE = "Bearer";
    private static final String TOKEN_CATEGORY = "category";
    private static final String ACCESS_TOKEN = "access";
    private static final String REFRESH_TOKEN = "refresh";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration-ms}")
    private long accessTokenExpirationMs;

    @Value("${jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpirationMs;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public AuthResponseDTO.TokenResDTO createTokenResponse(User user) {
        return AuthResponseDTO.TokenResDTO.builder()
                .tokenType(TOKEN_TYPE)
                .accessToken(createAccessToken(user))
                .refreshToken(createRefreshToken(user))
                .build();
    }

    public String createAccessToken(User user) {
        return createToken(user, ACCESS_TOKEN, accessTokenExpirationMs);
    }

    public String createRefreshToken(User user) {
        return createToken(user, REFRESH_TOKEN, refreshTokenExpirationMs);
    }

    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }

    public void validateAccessToken(String token) {
        validateToken(token, ACCESS_TOKEN, AuthErrorCode.INVALID_TOKEN);
    }

    public void validateRefreshToken(String token) {
        validateToken(token, REFRESH_TOKEN, AuthErrorCode.INVALID_REFRESH_TOKEN);
    }

    private String createToken(User user, String category, long expirationMs) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getUserId())
                .claim("nickname", user.getNickname())
                .claim(TOKEN_CATEGORY, category)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    private void validateToken(String token, String expectedCategory, AuthErrorCode invalidTokenErrorCode) {
        Claims claims = parseClaims(token);
        String category = claims.get(TOKEN_CATEGORY, String.class);

        if (!expectedCategory.equals(category)) {
            throw new AuthException(invalidTokenErrorCode);
        }
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new AuthException(AuthErrorCode.EXPIRED_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
    }
}
