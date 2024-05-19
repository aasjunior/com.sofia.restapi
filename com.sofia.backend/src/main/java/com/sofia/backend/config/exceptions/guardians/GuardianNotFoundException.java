package com.sofia.backend.config.exceptions.guardians;

public class GuardianNotFoundException extends RuntimeException{
    public GuardianNotFoundException(String message){
        super(message);
    }
}
