package com.sofia.backend.domain.model.healthprofessional;

import com.sofia.backend.domain.model.common.enums.ProfessionalRegistrationType;
import com.sofia.backend.domain.model.user.UserResponse;

public record HealthProfessionalResponse(
        String id,
        String firstName,
        String lastName,
        String registrationNumber,
        ProfessionalRegistrationType registrationType,
        String specialty,
        UserResponse user
){
}
