package com.sofia.backend.domain.model.login;

public record LoginRequest(
        String email,
        String password
) {
}
