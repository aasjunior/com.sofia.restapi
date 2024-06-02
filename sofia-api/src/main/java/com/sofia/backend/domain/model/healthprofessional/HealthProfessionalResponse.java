package com.sofia.backend.domain.model.healthprofessional;

import com.sofia.backend.domain.model.common.enums.ProfessionalRegistrationType;
import com.sofia.backend.domain.model.user.UserResponse;

public record HealthProfessionalResponse(
        String id,
        String registrationNumber,
        ProfessionalRegistrationType registrationType,
        String specialty,
        UserResponse user
){
}
