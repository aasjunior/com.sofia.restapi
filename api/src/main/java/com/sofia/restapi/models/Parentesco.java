package com.sofia.restapi.models;

public enum Parentesco{
    MAE("Mãe"),
    PAI("Pai"),
    IRMA("Irmã"),
    IRMAO("Irmão"),
    TIA("Tia"),
    TIO("Tio"),
    AVO1("Avó"),
    AVO2("Avô");
    
    private String descricao;
    
    Parentesco(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
