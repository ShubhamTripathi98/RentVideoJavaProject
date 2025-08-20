package com.example.rentvideo.video;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {
    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    // List all available videos (authenticated users)
    @GetMapping
    public List<VideoEntity> getAllVideos() {
        return videoService.getAllVideos();
    }

    // Create a new video (ADMIN only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VideoEntity> createVideo(@RequestBody VideoEntity video) {
        return ResponseEntity.ok(videoService.createVideo(video));
    }

    // Update a video (ADMIN only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VideoEntity> updateVideo(@PathVariable Long id, @RequestBody VideoEntity video) {
        try {
            return videoService.updateVideo(id, video)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (VideoNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a video (ADMIN only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        try {
            videoService.deleteVideo(id);
            return ResponseEntity.noContent().build();
        } catch (VideoNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get a video by id (authenticated users)
    @GetMapping("/{id}")
    public ResponseEntity<VideoEntity> getVideoById(@PathVariable Long id) {
        return videoService.getVideoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
