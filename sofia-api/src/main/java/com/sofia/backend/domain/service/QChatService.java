package com.sofia.backend.domain.service;

import com.sofia.backend.domain.model.qchat.QChat;
import com.sofia.backend.domain.model.qchat.QChatRequest;
import com.sofia.backend.domain.repository.QChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QChatService {
    private final QChatRepository qchatRepository;

    public ResponseEntity<?> submitQChat(QChatRequest request){
        try{
            QChat qChat = QChat.fromRequest(request);
            QChat saved = qchatRepository.save(qChat);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
