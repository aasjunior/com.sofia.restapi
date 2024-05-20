package com.sofia.backend.domain.model.healthprofessional;

import com.sofia.backend.domain.model.common.enums.ProfessionalRegistrationType;
import com.sofia.backend.domain.model.user.UserRequest;

public record HealthProfessionalRequest(
        String firstName,
        String lastName,
        String registrationNumber,
        ProfessionalRegistrationType registrationType,
        String specialty,
        UserRequest user
) {
}
