package com.sofia.backend.domain.model.checklist.qchat;

import java.util.Map;

public record QChatRequest(
        String patientId,
        Map<String, Boolean> questions
) {
}
