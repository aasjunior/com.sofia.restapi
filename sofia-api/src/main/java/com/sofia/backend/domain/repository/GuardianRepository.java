package com.sofia.backend.domain.repository;

import com.sofia.backend.domain.model.guardian.Guardian;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GuardianRepository extends MongoRepository<Guardian, String> {
}
