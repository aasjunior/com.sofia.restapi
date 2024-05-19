package com.sofia.backend.domain.model.common.converter;

import com.sofia.backend.domain.model.user.User;
import com.sofia.backend.domain.model.user.UserResponse;

public class Converter {
    public static UserResponse toResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getRegistrationDate()
        );
    }
}
