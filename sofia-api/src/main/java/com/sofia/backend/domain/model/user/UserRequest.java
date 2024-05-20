package com.sofia.backend.domain.model.user;

import com.sofia.backend.domain.model.common.enums.UserRole;

import java.time.LocalDateTime;

public record UserRequest(
        String username,
        String email,
        String password,
        UserRole role
) {
}
