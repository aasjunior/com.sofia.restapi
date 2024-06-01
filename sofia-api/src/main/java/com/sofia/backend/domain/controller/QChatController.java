package com.sofia.backend.domain.controller;

import com.sofia.backend.config.exceptions.checklist.TestNotFoundException;
import com.sofia.backend.domain.model.checklist.qchat.QChat;
import com.sofia.backend.domain.model.checklist.qchat.QChatRequest;
import com.sofia.backend.domain.model.checklist.qchat.TestResponse;
import com.sofia.backend.domain.service.QChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/checklist/qchat")
public class QChatController {
    private final QChatService qchatService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitQChat(@RequestBody QChatRequest request){
        return qchatService.submitQChat(request);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<TestResponse> getQChat(@PathVariable String patientId){
        try {
            TestResponse response = qchatService.getQChat(patientId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(TestNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/responses/{testId}")
    public ResponseEntity<QChat> getQChatResponses(@PathVariable String testId){
        try{
            QChat response = qchatService.getQChatResponses(testId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(TestNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
