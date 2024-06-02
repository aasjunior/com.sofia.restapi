package com.sofia.backend.domain.repository;

import com.sofia.backend.domain.model.checklist.qchat.QChat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface QChatRepository extends MongoRepository<QChat, String> {
    Optional<QChat> findByPatientId(String patientId);
}
