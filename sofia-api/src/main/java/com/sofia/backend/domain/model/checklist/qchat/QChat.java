package com.sofia.backend.domain.model.checklist.qchat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "qchat")
public class QChat {
    @Id
    private String id;
    private String patientId;
    private Map<String, Boolean> questions;
    private LocalDateTime registerDate;
    private QChatResponse responses;

    public static QChat fromRequest(QChatRequest request, QChatResponse responses){
        return new QChat(
                null,
                request.patientId(),
                request.questions(),
                LocalDateTime.now(),
                responses
        );
    }
}
