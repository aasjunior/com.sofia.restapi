package com.sofia.restapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sofia.restapi.models.Responsavel;

@Repository
public interface ResponsavelRepository extends CrudRepository<Responsavel, Long>{
}
