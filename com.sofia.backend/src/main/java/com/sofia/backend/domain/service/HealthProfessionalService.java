package com.sofia.backend.domain.service;

import com.sofia.backend.domain.model.common.converter.Converter;
import com.sofia.backend.domain.model.healthprofessional.HealthProfessional;
import com.sofia.backend.domain.model.healthprofessional.HealthProfessionalRequest;
import com.sofia.backend.domain.model.healthprofessional.HealthProfessionalResponse;
import com.sofia.backend.domain.model.user.User;
import com.sofia.backend.domain.model.user.UserRequest;
import com.sofia.backend.domain.repository.HealthProfessionalRepository;
import com.sofia.backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthProfessionalService {
    private final HealthProfessionalRepository healthProfessionalRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public HealthProfessionalResponse register(HealthProfessionalRequest request){
        UserRequest userRequest = request.user();
        if(userRepository.findByUsername(userRequest.username()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        String encryptedPassword = passwordEncoder.encode(userRequest.password());
        User user = User.fromRequest(userRequest, encryptedPassword);
        user = userRepository.save(user);

        HealthProfessional healthProfessional = HealthProfessional.fromRequest(request, user);
        healthProfessionalRepository.save(healthProfessional);
        return Converter.toResponse(healthProfessional);
    }
}
