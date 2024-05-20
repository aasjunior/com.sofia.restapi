package com.sofia.backend.domain.model.common.enums;

public enum ProfessionalRegistrationType{
    // Tipos de registro no Brasil
    BR_CRM("CRM"), // Conselho Regional de Medicina
    BR_CRP("CRP"), // Conselho Regional de Psicologia
    BR_COREN("COREN"), // Conselho Regional de Enfermagem

    // Tipos de registro nos EUA
    EUA_MEDICAL_BOARD("Medical Board"), // Medical Board
    EUA_APA("APA"), // American Psychological Association
    EUA_NURSING_BOARD("Nursing Board"), // Nursing Board

    // Caso nenhum dos acima se aplique
    NONE("None");

    // Campo para armazenar a string do tipo de registro
    private String registrationType;

    // Construtor para inicializar o campo registrationType
    ProfessionalRegistrationType(String registrationType){
        this.registrationType = registrationType;
    }

    // Método getter para recuperar o valor de registrationType
    public String getRegistrationType(){
        return registrationType;
    }
}
