package com.sofia.restapi.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sobrenome;
    private LocalDate dataNascimento;
    private Boolean casosFamilia;
    private Boolean complicacoesGravidez;
    private Boolean prematuro;
    private LocalDateTime dataCadastro;
    
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    private Etnia etnia;

    @ManyToOne
    private Responsavel responsavel;

    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Boolean getCasosFamilia() {
        return casosFamilia;
    }

    public void setCasosFamilia(Boolean casosFamilia) {
        this.casosFamilia = casosFamilia;
    }

    public Boolean getComplicacoesGravidez() {
        return complicacoesGravidez;
    }

    public void setComplicacoesGravidez(Boolean complicacoesGravidez) {
        this.complicacoesGravidez = complicacoesGravidez;
    }

    public Boolean getPrematuro() {
        return prematuro;
    }

    public void setPrematuro(Boolean prematuro) {
        this.prematuro = prematuro;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Etnia getEtnia() {
        return etnia;
    }

    public void setEtnia(Etnia etnia) {
        this.etnia = etnia;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

}
