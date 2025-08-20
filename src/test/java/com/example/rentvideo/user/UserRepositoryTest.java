package com.example.rentvideo.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_and_existsByEmail() {
        UserEntity user = UserEntity.builder()
                .email("repo@example.com")
                .passwordHash("hash")
                .firstName("Repo")
                .lastName("User")
                .role(Role.CUSTOMER)
                .enabled(true)
                .build();
        userRepository.save(user);
        assertTrue(userRepository.findByEmail("repo@example.com").isPresent());
        assertTrue(userRepository.existsByEmail("repo@example.com"));
        assertFalse(userRepository.findByEmail("notfound@example.com").isPresent());
        assertFalse(userRepository.existsByEmail("notfound@example.com"));
    }
}

