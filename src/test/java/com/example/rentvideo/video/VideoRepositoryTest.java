package com.example.rentvideo.video;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VideoRepositoryTest {
    @Autowired
    private VideoRepository videoRepository;

    @Test
    void findByAvailableTrue_returnsOnlyAvailable() {
        VideoEntity available = VideoEntity.builder().title("A").director("D").genre("G").available(true).build();
        VideoEntity notAvailable = VideoEntity.builder().title("B").director("D").genre("G").available(false).build();
        videoRepository.save(available);
        videoRepository.save(notAvailable);
        List<VideoEntity> result = videoRepository.findByAvailableTrue();
        assertTrue(result.stream().anyMatch(v -> v.getTitle().equals("A")));
        assertFalse(result.stream().anyMatch(v -> v.getTitle().equals("B")));
    }
}
