package com.sofia.backend.domain.model.guardian;

import com.sofia.backend.domain.model.patientguardian.PatientGuardian;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "guardians")
public class Guardian {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    private HashMap<String, PatientGuardian> patients;

    public void addPatient(PatientGuardian patientGuardian){
        this.patients.put(patientGuardian.patientId(), patientGuardian);
    }

    public static Guardian fromRequest(GuardianRequest request){
        return new Guardian(
                null,
                request.firstName(),
                request.lastName(),
                request.phone(),
                request.email(),
                new HashMap<>()
        );
    }
}
