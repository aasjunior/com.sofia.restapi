package com.sofia.backend.domain.service;

import com.sofia.backend.config.exceptions.patient.InvalidPatientIdException;
import com.sofia.backend.config.exceptions.patient.PatientNotFoundException;
import com.sofia.backend.domain.model.guardian.Guardian;
import com.sofia.backend.domain.model.patient.Patient;
import com.sofia.backend.domain.model.patient.PatientRequest;
import com.sofia.backend.domain.model.patientguardian.PatientGuardian;
import com.sofia.backend.domain.model.patientguardian.PatientGuardianRequest;
import com.sofia.backend.domain.repository.GuardianRepository;
import com.sofia.backend.domain.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final GuardianService guardianService;
    private final GuardianRepository guardianRepository;

    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(String id){
        return patientRepository.findById(id);
    }

    public ResponseEntity<Patient> createPatient(PatientRequest patientRequest){
        try{
            Patient savedPatient = patientRepository.save(Patient.fromRequest(patientRequest));
            return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Patient> addGuardianToPatient(String patientId, Guardian guardian){
        Optional<Patient> patientData = patientRepository.findById(patientId);

        if(patientData.isPresent()){
            Patient patient = patientData.get();
            patient.addGuardian(guardian);
            return new ResponseEntity<>(patientRepository.save(patient), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Patient> createPatientWithGuardian(PatientGuardianRequest request){
        try{
            Patient patient = patientRepository.save(
                    Patient.fromRequest(request.patientRequest())
            );

            ResponseEntity<Guardian> response = guardianService.createGuardian(
                    Guardian.fromRequest(request.guardianRequest())
            );

            if(response.getStatusCode() == HttpStatus.CREATED){
                Guardian guardian = response.getBody();

                PatientGuardian patientGuardian = new PatientGuardian(
                        patient.getId(),
                        guardian.getId(),
                        request.kinship()
                );
                guardian.addPatient(patientGuardian);
                addGuardianToPatient(patient.getId(), guardian);
                guardianService.addPatientToGuardian(patientGuardian);
            }else{
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            Optional<Patient> optionalPatient = patientRepository.findById(patient.getId());
            if(optionalPatient.isPresent()){
                return new ResponseEntity<>(optionalPatient.get(), HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Patient> updatePatientWithGuardian(String id, PatientGuardianRequest request){
        try{
            Optional<Patient> optionalPatient = patientRepository.findById(id);
            if(optionalPatient.isPresent()){
                Patient patient = optionalPatient.get();
                patient.updateFromRequest(request.patientRequest());

                ResponseEntity<Guardian> response = guardianService.updateGuardian(
                        patient.getGuardians().values().stream().findFirst().orElse(null).getId(),
                        request.guardianRequest()
                );

                if(response.getStatusCode() == HttpStatus.OK){
                    Guardian guardian = response.getBody();

                    PatientGuardian patientGuardian = new PatientGuardian(
                            patient.getId(),
                            guardian.getId(),
                            request.kinship()
                    );
                    guardian.addPatient(patientGuardian);
                    patientRepository.save(patient);
                }else{
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }

                return new ResponseEntity<>(patient, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Patient> updatePatient(String id, PatientRequest request){
        try{
            Optional<Patient> patientData = patientRepository.findById(id);
            if(patientData.isPresent()){
                Patient patient = patientData.get();
                // Atualize os campos do patient com os dados do request
                // ...
                Patient updatedPatient = patientRepository.save(patient);
                return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public void deletePatient(String patientId){
        Optional<Patient> patientData = patientRepository.findById(patientId);
        if(patientData.isPresent()){
            Patient patient = patientData.get();
            List<Guardian> guardians = new ArrayList<>(patient.getGuardians().values());

            Guardian guardian = guardians.get(0);

            if(guardians.size() == 1){
                guardianRepository.deleteById(guardian.getId());
            }else{
                guardians.removeIf(g ->
                    g.getPatients().equals(patientId)
                );
                guardianRepository.save(guardian);
            }
            patientRepository.deleteById(patientId);
        }else{
            throw new PatientNotFoundException("Paciente não encontrado com o ID: " + patientId);
        }
    }


    private boolean isValidPatientId(String patientId){
        if(patientId == null || patientId.isEmpty()){
            throw new InvalidPatientIdException("ID do paciente inválido.");
        }
        return true;
    }
}
