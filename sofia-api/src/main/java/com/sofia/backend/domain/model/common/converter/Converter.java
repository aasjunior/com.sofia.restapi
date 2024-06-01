package com.sofia.backend.domain.model.common.converter;

import com.sofia.backend.domain.model.healthprofessional.HealthProfessional;
import com.sofia.backend.domain.model.healthprofessional.HealthProfessionalResponse;
import com.sofia.backend.domain.model.user.User;
import com.sofia.backend.domain.model.user.UserResponse;

public class Converter {
    public static UserResponse toResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getFirstName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getRegistrationDate()
        );
    }

    public static HealthProfessionalResponse toResponse(HealthProfessional healthProfessional){
        return new HealthProfessionalResponse(
                healthProfessional.getId(),
                healthProfessional.getRegistrationNumber(),
                healthProfessional.getRegistrationType(),
                healthProfessional.getSpecialty(),
                Converter.toResponse(healthProfessional.getUser())
        );
    }
}
