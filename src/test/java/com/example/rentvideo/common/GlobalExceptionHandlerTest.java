package com.example.rentvideo.common;

import com.example.rentvideo.auth.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private com.example.rentvideo.auth.AuthService authService;

    @Test
    void validationError_returnsBadRequest() throws Exception {
        String json = "{" +
                "\"email\":\"not-an-email\"," +
                "\"password\":\"\"," +
                "\"firstName\":\"\"," +
                "\"lastName\":\"\"}";
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.fieldErrors").exists());
    }

    @Test
    void businessError_returnsBadRequest() throws Exception {
        doThrow(new IllegalArgumentException("Email already registered")).when(authService).register(org.mockito.Mockito.any());
        String json = "{" +
                "\"email\":\"test@example.com\"," +
                "\"password\":\"pass\"," +
                "\"firstName\":\"First\"," +
                "\"lastName\":\"Last\"}";
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email already registered"))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.status").value(400));
    }
}
