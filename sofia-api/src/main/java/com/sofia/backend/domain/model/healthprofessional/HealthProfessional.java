package com.sofia.backend.domain.model.healthprofessional;

import com.sofia.backend.domain.model.common.enums.ProfessionalRegistrationType;
import com.sofia.backend.domain.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "health_professionals")
public class HealthProfessional {
    @Id
    private String id;
    private String registrationNumber;
    private ProfessionalRegistrationType registrationType;
    private String specialty;
    private User user;

    public static HealthProfessional fromRequest(HealthProfessionalRequest request, User user){
        return new HealthProfessional(
                null,
                request.registrationNumber(),
                request.registrationType(),
                request.specialty(),
                user
        );
    }
}
