package com.sofia.backend.domain.model.checklist.qchat;

import com.sofia.backend.domain.model.common.enums.Checklist;
import com.sofia.backend.domain.model.common.enums.ChecklistType;

import java.time.LocalDateTime;

public record TestResponse(
        String testId,
        Checklist testName,
        ChecklistType testType,
        LocalDateTime registerDateTime,
        Boolean result
) {
}
