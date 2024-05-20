package com.sofia.backend.domain.model.patientguardian;

import com.sofia.backend.domain.model.common.enums.Kinship;
import com.sofia.backend.domain.model.guardian.GuardianRequest;
import com.sofia.backend.domain.model.patient.PatientRequest;

public record PatientGuardianRequest(
        PatientRequest patientRequest,
        GuardianRequest guardianRequest,
        Kinship kinship
) {
}
