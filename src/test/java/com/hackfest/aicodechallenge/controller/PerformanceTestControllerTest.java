package com.hackfest.aicodechallenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackfest.aicodechallenge.model.dto.PerformanceTestRequest;
import com.hackfest.aicodechallenge.service.PerformanceTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PerformanceTestController.class)
class PerformanceTestControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PerformanceTestService performanceTestService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void testCreatePerformanceTest() throws Exception {
        PerformanceTestRequest request = new PerformanceTestRequest(
                "Load Test 1", 60, 10, "Test description"
        );
        
        mockMvc.perform(post("/api/v1/performance/tests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
    
    @Test
    void testHealthCheck() throws Exception {
        mockMvc.perform(get("/api/v1/performance/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
