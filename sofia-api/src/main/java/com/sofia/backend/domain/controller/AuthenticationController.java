package com.sofia.backend.domain.controller;

import com.sofia.backend.config.exceptions.user.UserNotFoundException;
import com.sofia.backend.domain.model.login.LoginRequest;
import com.sofia.backend.domain.model.login.LoginResponse;
import com.sofia.backend.domain.model.user.User;
import com.sofia.backend.domain.model.user.UserRequest;
import com.sofia.backend.domain.model.user.UserResponse;
import com.sofia.backend.domain.repository.UserRepository;
import com.sofia.backend.domain.service.UserService;
import com.sofia.backend.domain.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request){
        try{
            LoginResponse loginResponse = authenticationService.login(request);
            return ResponseEntity.ok(loginResponse);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest request) {
        try {
            UserResponse user = authenticationService.register(request);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity("Username already exists", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new  ResponseEntity("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        try {
            User user = userService.findByUsernameSecret(username);
            if(user != null){
                return ResponseEntity.ok(user);
            }else{
                throw new UserNotFoundException("User Not Found");
            }
        }catch(UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
