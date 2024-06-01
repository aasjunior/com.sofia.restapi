package com.sofia.backend.domain.service;

import com.sofia.backend.config.exceptions.user.UserNotFoundException;
import com.sofia.backend.domain.model.common.converter.Converter;
import com.sofia.backend.domain.model.user.User;
import com.sofia.backend.domain.model.user.UserResponse;
import com.sofia.backend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(Converter::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponse findByUsername(String username){
        User user = (User) userRepository.findByUsername(username);
        return Converter.toResponse(user);
    }

    public User findByUsernameSecret(String username){
        return (User) userRepository.findByUsername(username);
    }

    public void delete(String userId){
        User user = userRepository
                .findById(userId)
                .orElseThrow( () ->
                    new UserNotFoundException("User Not Found")
                );
        userRepository.delete(user);
    }
}
