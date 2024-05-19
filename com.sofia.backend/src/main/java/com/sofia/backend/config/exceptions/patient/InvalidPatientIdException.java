package com.sofia.backend.config.exceptions.patient;

public class InvalidPatientIdException extends RuntimeException {
    public InvalidPatientIdException(String message) {
        super(message);
    }
}
