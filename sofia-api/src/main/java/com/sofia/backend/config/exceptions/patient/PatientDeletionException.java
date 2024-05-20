package com.sofia.backend.config.exceptions.patient;

public class PatientDeletionException extends RuntimeException{
    public PatientDeletionException(String message){
        super(message);
    }
}
