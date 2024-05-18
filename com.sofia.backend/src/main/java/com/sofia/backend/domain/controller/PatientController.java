package com.sofia.backend.domain.controller;

import com.sofia.backend.domain.model.patient.Patient;
import com.sofia.backend.domain.model.patient.PatientRequest;
import com.sofia.backend.domain.model.patientguardian.PatientGuardianRequest;
import com.sofia.backend.domain.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients(){
        List<Patient> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id){
        Optional<Patient> patient = patientService.getPatientById(id);
        return patient
                .map(value ->
                        new ResponseEntity<>(value, HttpStatus.OK)
                )
                .orElseGet(() ->
                        new ResponseEntity<>(HttpStatus.NOT_FOUND)
                );
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody PatientRequest request){
        return patientService.createPatient(request);
    }

    @PostMapping("/patient-with-guardian")
    public ResponseEntity<Patient> createPatientWithGuardian(@RequestBody PatientGuardianRequest request){
        return patientService.createPatientWithGuardian(request);
    }
}
