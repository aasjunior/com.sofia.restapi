package com.sofia.backend.domain.model.qchat;

import java.util.Map;

public record QChatRequest(
        String patientId,
        Map<String, Boolean> questions
) {
}
