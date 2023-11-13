package com.sofia.restapi.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sofia.restapi.repository.PacienteRepository;
import com.sofia.restapi.repository.ResponsavelRepository;
import com.sofia.restapi.models.Paciente;
import com.sofia.restapi.models.Responsavel;

@RestController
public class PacienteController {
    @Autowired
    private PacienteRepository action;

    @Autowired
    private ResponsavelRepository responsavelRepository;


    @GetMapping("/pacientes")
    public List<Paciente> listPacientes(){
        return action.findAll();
    }

    @PostMapping("/pacientes")
    public ResponseEntity<Paciente> insertPaciente(@RequestBody Paciente paciente){
        Responsavel responsavel = paciente.getResponsavel();
        if (responsavel != null) {
            responsavelRepository.save(responsavel);
        }
        Paciente novoPaciente = action.save(paciente);
        return ResponseEntity.ok(novoPaciente);
    }

    @GetMapping("/pacientes/{id}")
    public ResponseEntity<Paciente> getPacienteById(@PathVariable Long id){
        Optional<Paciente> paciente = action.findById(id);
        if (paciente.isPresent()) {
            return ResponseEntity.ok(paciente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/pacientes")
    public Paciente edit(@RequestBody Paciente p){
    	return action.save(p);
    }

    @DeleteMapping("/pacientes/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id){
        ResponseEntity<Paciente> responseEntity = getPacienteById(id);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Paciente p = responseEntity.getBody();
            action.delete(p);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pacientes/count")
    public Long countPacientes(){
        return action.count();
    } 

    @GetMapping("/pacientes/sort-name")
    public List<Paciente> sortNameAsc(){
        return action.findByOrderByNomeAsc();
    }

/*
    @GetMapping("/api/sortByIdadeDesc/{name}")
    public List<Paciente> sortByIdadeDesc(@PathVariable String name){
        return action.findByNomeOrderByIdadeDesc(name);
    }
*/
    @PostMapping("/pacientes/echo")
    public Paciente paciente(@RequestBody Paciente p){
        return p;
    }

    @GetMapping("/pacientes/search-name-contains/{keyword}")
    public List<Paciente> nameContain(@PathVariable String keyword){
        return action.findByNomeContaining(keyword);
    }
    
    @GetMapping("/pacientes/starts-with/{keyword}")
    public List<Paciente> startWith(@PathVariable String keyword){
        return action.findByNomeStartsWith(keyword);
    }
    
    @GetMapping("/pacientes/ends-with/{keyword}")
    public List<Paciente> endWith(@PathVariable String keyword){
        return action.findByNomeEndsWith(keyword);
    }   
}