package com.sofia.restapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sofia.restapi.models.Paciente;

@Repository
public interface PacienteRepository extends CrudRepository<Paciente, Long>{
    List<Paciente> findAll();
    Optional<Paciente> findById(Long id);
    List<Paciente> findByOrderByNomeAsc();
    //List<Paciente> findByNomeOrderByIdadeDesc(String nome);
    List<Paciente> findByNomeContaining(String keyword);
    List<Paciente> findByNomeStartsWith(String keyword);
    List<Paciente> findByNomeEndsWith(String keyword);
}
