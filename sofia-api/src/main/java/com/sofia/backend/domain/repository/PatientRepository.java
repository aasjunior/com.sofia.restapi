package com.sofia.backend.domain.repository;

import com.sofia.backend.domain.model.patient.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientRepository extends MongoRepository<Patient, String> {
}
