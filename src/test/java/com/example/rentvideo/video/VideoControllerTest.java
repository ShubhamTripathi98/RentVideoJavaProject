package com.example.rentvideo.video;

import com.example.rentvideo.auth.TestSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VideoController.class)
@Import(TestSecurityConfig.class)
class VideoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoService videoService;

    @Test
    @WithMockUser
    void getAllVideos_authenticated() throws Exception {
        when(videoService.getAllVideos()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/videos"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllVideos_unauthenticated() throws Exception {
        mockMvc.perform(get("/api/videos"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createVideo_admin() throws Exception {
        VideoEntity video = VideoEntity.builder()
                .title("Test")
                .director("Dir")
                .genre("G")
                .build();
        when(videoService.createVideo(any())).thenReturn(video);

        mockMvc.perform(post("/api/videos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(video)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void createVideo_customer_forbidden() throws Exception {
        VideoEntity video = VideoEntity.builder()
                .title("Test")
                .director("Dir")
                .genre("G")
                .build();

        mockMvc.perform(post("/api/videos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(video)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateVideo_notFound_returnsNotFound() throws Exception {
        when(videoService.updateVideo(anyLong(), any())).thenReturn(Optional.empty());

        VideoEntity video = VideoEntity.builder()
                .title("Test")
                .director("Dir")
                .genre("G")
                .build();

        mockMvc.perform(put("/api/videos/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(video)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteVideo_notFound_returnsNotFound() throws Exception {
        doThrow(new VideoNotFoundException("Video not found")).when(videoService).deleteVideo(99L);
        mockMvc.perform(delete("/api/videos/99"))
                .andExpect(status().isNotFound());
    }
}
