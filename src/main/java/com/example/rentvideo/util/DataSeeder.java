package com.example.rentvideo.util;

import com.example.rentvideo.user.Role;
import com.example.rentvideo.user.UserEntity;
import com.example.rentvideo.user.UserRepository;
import com.example.rentvideo.video.VideoEntity;
import com.example.rentvideo.video.VideoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void seed() {
        if (userRepository.count() == 0) {
            userRepository.save(UserEntity.builder()
                    .email("admin@rentvideo.com")
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .firstName("Admin")
                    .lastName("User")
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build());

            userRepository.save(UserEntity.builder()
                    .email("customer@rentvideo.com")
                    .passwordHash(passwordEncoder.encode("customer123"))
                    .firstName("Customer")
                    .lastName("User")
                    .role(Role.CUSTOMER)
                    .enabled(true)
                    .build());
        }

        if (videoRepository.count() == 0) {
            videoRepository.save(VideoEntity.builder()
                    .title("The Matrix")
                    .director("Wachowski Sisters")
                    .genre("Sci-Fi")
                    .available(true)
                    .build());

            videoRepository.save(VideoEntity.builder()
                    .title("Inception")
                    .director("Christopher Nolan")
                    .genre("Action")
                    .available(true)
                    .build());

            videoRepository.save(VideoEntity.builder()
                    .title("The Godfather")
                    .director("Francis Ford Coppola")
                    .genre("Crime")
                    .available(true)
                    .build());
        }
    }
}
