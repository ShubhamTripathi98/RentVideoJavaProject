package com.example.rentvideo.auth;

import com.example.rentvideo.user.Role;
import com.example.rentvideo.user.UserEntity;
import com.example.rentvideo.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    private UserRepository userRepository;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        authService = new AuthService(userRepository);
    }

    @Test
    void register_successful() {
        RegisterRequest req = new RegisterRequest("test@example.com", "pass", "First", "Last", Role.ADMIN);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        authService.register(req);
        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        UserEntity saved = captor.getValue();
        assertEquals("test@example.com", saved.getEmail());
        assertTrue(new BCryptPasswordEncoder().matches("pass", saved.getPasswordHash()));
        assertEquals(Role.ADMIN, saved.getRole());
    }

    @Test
    void register_duplicateEmail_throws() {
        RegisterRequest req = new RegisterRequest("test@example.com", "pass", "First", "Last", null);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new UserEntity()));
        assertThrows(IllegalArgumentException.class, () -> authService.register(req));
    }

    @Test
    void register_defaultsToCustomer() {
        RegisterRequest req = new RegisterRequest("test2@example.com", "pass", "First", "Last", null);
        when(userRepository.findByEmail("test2@example.com")).thenReturn(Optional.empty());
        authService.register(req);
        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        assertEquals(Role.CUSTOMER, captor.getValue().getRole());
    }
}

