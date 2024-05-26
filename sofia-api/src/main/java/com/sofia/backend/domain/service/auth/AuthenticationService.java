package com.sofia.backend.domain.service.auth;

import com.sofia.backend.domain.model.common.converter.Converter;
import com.sofia.backend.domain.model.login.LoginRequest;
import com.sofia.backend.domain.model.login.LoginResponse;
import com.sofia.backend.domain.model.login.RefreshRequest;
import com.sofia.backend.domain.model.user.User;
import com.sofia.backend.domain.model.user.UserRequest;
import com.sofia.backend.domain.model.user.UserResponse;
import com.sofia.backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public LoginResponse login(LoginRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        );
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var accessToken = tokenService.generateToken((User) auth.getPrincipal());
        var refreshToken = tokenService.generateRefreshToken((User) auth.getPrincipal());
        if(accessToken != null && refreshToken != null){
            return new LoginResponse(accessToken, refreshToken);
        }else{
            throw new RuntimeException("Erro durante a autenticação do usuário");
        }
    }

    public LoginResponse refresh(RefreshRequest request) {
        var username = tokenService.validateToken(request.token());
        UserDetails user = userRepository.findByUsername(username);

        if(user != null){
            var newAccessToken = tokenService.generateToken((User) user);
            var newRefreshToken = tokenService.generateRefreshToken((User) user);
            return new LoginResponse(newAccessToken, newRefreshToken);
        }else{
            throw new RuntimeException("Erro durante a atualização do token");
        }
    }


    public UserResponse register(UserRequest request){
        if (userRepository.findByUsername(request.username()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        String encryptedPassword = passwordEncoder.encode(request.password());
        User user = User.fromRequest(request, encryptedPassword);
        user = userRepository.save(user);
        return Converter.toResponse(user);
    }
}
