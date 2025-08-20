package com.example.rentvideo.video;

public class VideoNotFoundException extends RuntimeException {
    public VideoNotFoundException(Long id) {
        super("Video with id " + id + " not found");
    }
}

