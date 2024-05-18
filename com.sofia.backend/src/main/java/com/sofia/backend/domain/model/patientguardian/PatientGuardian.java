package com.sofia.backend.domain.model.patientguardian;

import com.sofia.backend.domain.model.common.enums.Kinship;

public record PatientGuardian(
        String patientId,
        String guardianId,
        Kinship kinship
) {
}
