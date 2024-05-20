package com.sofia.backend.domain.model.patient;

import com.sofia.backend.domain.model.common.enums.Ethnicity;
import com.sofia.backend.domain.model.common.enums.Gender;

public record PatientRequest(
        String firstName,
        String lastName,
        String birthDate,
        Gender gender,
        Ethnicity ethnicity,
        Boolean familyCases,
        Boolean pregnancyComplication,
        Boolean premature
) {
}
