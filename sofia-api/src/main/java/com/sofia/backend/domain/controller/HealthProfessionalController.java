package com.sofia.backend.domain.controller;

import com.sofia.backend.config.exceptions.ErrorResponse;
import com.sofia.backend.domain.model.healthprofessional.HealthProfessionalRequest;
import com.sofia.backend.domain.model.healthprofessional.HealthProfessionalResponse;
import com.sofia.backend.domain.service.HealthProfessionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/healthprofessional")
public class HealthProfessionalController {
    private final HealthProfessionalService healthProfessionalService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody HealthProfessionalRequest request){
        try {
            HealthProfessionalResponse response = healthProfessionalService.register(request);
            return ResponseEntity.ok(response);
        }catch(IllegalArgumentException e){
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
