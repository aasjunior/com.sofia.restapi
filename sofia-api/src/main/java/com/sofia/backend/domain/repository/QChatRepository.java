package com.sofia.backend.domain.repository;

import com.sofia.backend.domain.model.qchat.QChat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QChatRepository extends MongoRepository<QChat, String> {
}
