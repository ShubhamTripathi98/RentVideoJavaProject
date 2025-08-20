package com.example.rentvideo.video;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {
    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public List<VideoEntity> getAllVideos() {
        return videoRepository.findAll();
    }

    public Optional<VideoEntity> getVideoById(Long id) {
        return videoRepository.findById(id);
    }

    @Transactional
    public VideoEntity createVideo(VideoEntity video) {
        return videoRepository.save(video);
    }

    @Transactional
    public Optional<VideoEntity> updateVideo(Long id, VideoEntity updated) {
        VideoEntity video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException(id));
        video.setTitle(updated.getTitle());
        video.setDirector(updated.getDirector());
        video.setGenre(updated.getGenre());
        video.setAvailable(updated.isAvailable());
        return Optional.of(videoRepository.save(video));
    }

    @Transactional
    public void deleteVideo(Long id) {
        if (!videoRepository.existsById(id)) {
            throw new VideoNotFoundException(id);
        }
        videoRepository.deleteById(id);
    }
}
