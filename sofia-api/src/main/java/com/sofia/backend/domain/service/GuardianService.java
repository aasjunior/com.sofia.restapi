package com.sofia.backend.domain.service;

import com.sofia.backend.config.exceptions.guardians.GuardianNotFoundException;
import com.sofia.backend.domain.model.guardian.Guardian;
import com.sofia.backend.domain.model.guardian.GuardianRequest;
import com.sofia.backend.domain.model.patient.Patient;
import com.sofia.backend.domain.model.patientguardian.PatientGuardian;
import com.sofia.backend.domain.repository.GuardianRepository;
import com.sofia.backend.domain.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuardianService {
    private final GuardianRepository guardianRepository;
    private final PatientRepository patientRepository;

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

    public ResponseEntity<Guardian> updateGuardian(String id, GuardianRequest request){
        try{
            Optional<Guardian> optionalGuardian = guardianRepository.findById(id);
            if(optionalGuardian.isPresent()){
                Guardian guardian = optionalGuardian.get();
                guardian.updateFromRequest(request);
                Guardian updatedGuardian = guardianRepository.save(guardian);
                return new ResponseEntity<>(updatedGuardian, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public void deleteGuardian(String guardianId){
        Optional<Guardian> guardianData = guardianRepository.findById(guardianId);
        if(guardianData.isPresent()){
            Guardian guardian = guardianData.get();

            if(!guardian.getPatients().isEmpty()){
                for(PatientGuardian patientGuardian : guardian.getPatients().values()){
                    String patientId = patientGuardian.patientId();
                    Optional<Patient> patientData = patientRepository.findById(patientId);

                    if(patientData.isPresent()){
                        Patient patient = patientData.get();

                        if(patient.getGuardians().size() == 1){
                            patientRepository.deleteById(patientId);
                        }else{
                            patient.getGuardians().remove(guardianId);
                            patientRepository.save(patient);
                        }
                    }
                }
            }
            guardianRepository.deleteById(guardianId);
        }else{
            throw new GuardianNotFoundException("Responsavel não encontrado com o ID: " + guardianId);
        }
    }
}
