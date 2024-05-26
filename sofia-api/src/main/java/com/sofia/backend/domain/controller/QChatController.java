package com.sofia.backend.domain.controller;

import com.sofia.backend.domain.model.qchat.QChatRequest;
import com.sofia.backend.domain.service.QChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/checklist/qchat")
public class QChatController {
    private final QChatService qchatService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitQChat(@RequestBody QChatRequest request){
        return qchatService.submitQChat(request);
    }
}
