package com.sofia.backend.domain.model.login;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
