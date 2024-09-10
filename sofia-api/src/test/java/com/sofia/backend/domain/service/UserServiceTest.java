package com.sofia.backend.domain.service;
import com.sofia.backend.config.exceptions.user.UserNotFoundException;
import com.sofia.backend.domain.model.common.enums.UserRole;
import com.sofia.backend.domain.model.user.User;
import com.sofia.backend.domain.model.user.UserResponse;
import com.sofia.backend.domain.repository.UserRepository;
import com.sofia.backend.domain.model.common.converter.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    User user1 = new User("1", "John", "Doe", "johndoe", "john@example.com", "", UserRole.ADMIN, LocalDateTime.of(2023,4,9,8,7));
    User user2 = new User("2", "John", "Doe", "johndoe", "john@example.com", "", UserRole.USER, LocalDateTime.of(2023,4,9,8,7));

    UserResponse response1 = new UserResponse("1", "John", "Doe", "johndoe", "john@example.com", UserRole.ADMIN, LocalDateTime.of(2023,4,9,8,7));
    UserResponse response2 = new UserResponse("2", "John", "Doe", "johndoe", "john@example.com", UserRole.USER, LocalDateTime.of(2023,4,9,8,7));

    @Test
    void testGetAllUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        mockStatic(Converter.class);
        when(Converter.toResponse(user1)).thenReturn(response1);
        when(Converter.toResponse(user2)).thenReturn(response2);

        // Act
        List<UserResponse> result = userService.getAllUsers();

        // Assert
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_UserExists() {
        // Arrange
        when(userRepository.findById("1")).thenReturn(Optional.of(user1));

        when(Converter.toResponse(user1)).thenReturn(response1);

        // Act
        ResponseEntity<UserResponse> result = userService.getUserById("1");

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void testGetUserById_UserNotFound() {
        // Arrange
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<UserResponse> result = userService.getUserById("1");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void testFindByUsername() {
        // Arrange
        when(userRepository.findByUsername("johndoe")).thenReturn(user1);

        when(Converter.toResponse(user1)).thenReturn(response1);

        // Act
        UserResponse result = userService.findByUsername("johndoe");

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findByUsername("johndoe");
    }

    @Test
    void testDelete_UserExists() {
        // Arrange
        when(userRepository.findById("1")).thenReturn(Optional.of(user1));

        // Act
        userService.delete("1");

        // Assert
        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(1)).delete(user1);
    }

    @Test
    void testDelete_UserNotFound() {
        // Arrange
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.delete("1"));
        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(0)).delete(any(User.class));
    }


}