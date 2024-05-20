package com.sofia.backend.domain.repository;

import com.sofia.backend.domain.model.healthprofessional.HealthProfessional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HealthProfessionalRepository extends MongoRepository<HealthProfessional, String> {
}
