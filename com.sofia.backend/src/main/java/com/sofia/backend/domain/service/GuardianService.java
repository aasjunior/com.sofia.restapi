package com.sofia.backend.domain.service;

import com.sofia.backend.domain.model.guardian.Guardian;
import com.sofia.backend.domain.model.patientguardian.PatientGuardian;
import com.sofia.backend.domain.repository.GuardianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuardianService {
    private final GuardianRepository guardianRepository;

    public List<Guardian> getAllGuardians(){
        return guardianRepository.findAll();
    }

    public Optional<Guardian> getGuardianById(String id){
        return guardianRepository.findById(id);
    }

    public ResponseEntity<Guardian> createGuardian(Guardian guardian){
        try{
            Guardian savedGuardian = guardianRepository.save(guardian);
            return new ResponseEntity<>(savedGuardian, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Guardian> addPatientToGuardian(PatientGuardian patientGuardian){
        Optional<Guardian> guardianData = guardianRepository.findById(patientGuardian.guardianId());

        if(guardianData.isPresent()){
            Guardian guardian = guardianData.get();
            guardian.addPatient(patientGuardian);
            return new ResponseEntity<>(guardianRepository.save(guardian), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
