package com.sofia.backend.config.database;

import com.sofia.backend.domain.model.common.enums.Ethnicity;
import com.sofia.backend.domain.model.common.enums.Gender;
import com.sofia.backend.domain.model.common.enums.Kinship;
import com.sofia.backend.domain.model.common.enums.UserRole;
import com.sofia.backend.domain.model.guardian.Guardian;
import com.sofia.backend.domain.model.patient.Patient;
import com.sofia.backend.domain.model.patientguardian.PatientGuardian;
import com.sofia.backend.domain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class DatabasePopulator implements CommandLineRunner {

    private final MongoTemplate mongoTemplate;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Patient patient = createPatient();
        Guardian guardian = createGuardian();
        User user = createUser();

        Query query = new Query(
                Criteria.where("email").is(guardian.getEmail())
        );

        if(!mongoTemplate.exists(query, Guardian.class)){
            Guardian savedGuardian = mongoTemplate.save(guardian);
            Patient savedPatient = mongoTemplate.save(patient);

            PatientGuardian patientGuardian = new PatientGuardian(
                    savedPatient.getId(),
                    savedGuardian.getId(),
                    Kinship.Mother
            );

            savedGuardian.addPatient(patientGuardian);
            savedPatient.addGuardian(savedGuardian);

            mongoTemplate.save(savedPatient);
            mongoTemplate.save(savedGuardian);
        }

        Query userQuery = new Query(
                Criteria.where("email").is(user.getEmail())
        );

        if(!mongoTemplate.exists(userQuery, User.class)){
            mongoTemplate.save(user);
        }
    }

    private Patient createPatient(){
        return new Patient(
                null,
                "Luna",
                "Pedroso",
                "2022-04-21",
                Gender.Female,
                Ethnicity.Black,
                false,
                true,
                false,
                new HashMap<>(),
                LocalDateTime.now()
        );
    }

    private Guardian createGuardian(){
        return new Guardian(
                null,
                "Maria",
                "Pedroso",
                "(12) 34567-8901",
                "maria@email.com",
                new HashMap<>()
        );
    }

    private User createUser(){
        return new User(
                null,
                "John",
                "Doe",
                "john.doe",
                "john.doe@email.com",
                passwordEncoder.encode("123"),
                UserRole.USER,
                LocalDateTime.now()

        );
    }
}
