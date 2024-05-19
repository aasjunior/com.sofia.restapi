package com.sofia.backend.domain.model.user;

import com.sofia.backend.domain.model.common.enums.UserRole;

import java.time.LocalDateTime;

public record UserResponse(
        String id,
        String firstName,
        String lastName,
        String username,
        String email,
        UserRole role,
        LocalDateTime registrationDate
) {
}
