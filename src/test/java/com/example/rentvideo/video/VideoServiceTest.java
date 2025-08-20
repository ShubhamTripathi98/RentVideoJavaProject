package com.example.rentvideo.video;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VideoServiceTest {
    private VideoRepository videoRepository;
    private VideoService videoService;

    @BeforeEach
    void setUp() {
        videoRepository = mock(VideoRepository.class);
        videoService = new VideoService(videoRepository);
    }

    @Test
    void getAllVideos_returnsList() {
        List<VideoEntity> videos = Arrays.asList(
                VideoEntity.builder().title("A").director("D").genre("G").available(true).build(),
                VideoEntity.builder().title("B").director("D").genre("G").available(true).build()
        );
        when(videoRepository.findAll()).thenReturn(videos);
        assertEquals(2, videoService.getAllVideos().size());
    }

    @Test
    void createVideo_savesAndReturns() {
        VideoEntity video = VideoEntity.builder().title("Test").director("D").genre("G").available(true).build();
        when(videoRepository.save(video)).thenReturn(video);
        assertEquals("Test", videoService.createVideo(video).getTitle());
    }

    @Test
    void updateVideo_success() {
        VideoEntity existing = VideoEntity.builder().id(1L).title("Old").director("D").genre("G").available(true).build();
        VideoEntity updated = VideoEntity.builder().title("New").director("D").genre("G").available(true).build();
        when(videoRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(videoRepository.save(any())).thenReturn(existing);
        Optional<VideoEntity> result = videoService.updateVideo(1L, updated);
        assertTrue(result.isPresent());
        assertEquals("New", result.get().getTitle());
    }

    @Test
    void updateVideo_notFound() {
        when(videoRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(VideoNotFoundException.class, () -> videoService.updateVideo(2L, VideoEntity.builder().title("T").director("D").genre("G").available(true).build()));
    }

    @Test
    void deleteVideo_callsRepository() {
        when(videoRepository.existsById(1L)).thenReturn(true);
        videoService.deleteVideo(1L);
        verify(videoRepository).deleteById(1L);
    }

    @Test
    void deleteVideo_notFound() {
        when(videoRepository.existsById(2L)).thenReturn(false);
        assertThrows(VideoNotFoundException.class, () -> videoService.deleteVideo(2L));
    }
}
