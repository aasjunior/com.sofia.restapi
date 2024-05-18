package com.sofia.backend.domain.model.guardian;

public record GuardianRequest(
        String firstName,
        String lastName,
        String phone,
        String email
){
}
