package com.hackfest.aicodechallenge.service;

import com.hackfest.aicodechallenge.model.dto.PerformanceTestRequest;
import com.hackfest.aicodechallenge.model.dto.PerformanceTestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerformanceTestServiceTest {
    
    private PerformanceTestService performanceTestService;
    
    @BeforeEach
    void setUp() {
        performanceTestService = new PerformanceTestService();
    }
    
    @Test
    void testCreatePerformanceTest() {
        // Given
        PerformanceTestRequest request = new PerformanceTestRequest(
                "Test Load", 30, 5, "Sample test"
        );
        
        // When
        PerformanceTestResponse response = performanceTestService.createTest(request);
        
        // Then
        assertNotNull(response);
        assertEquals("Test Load", response.getTestName());
        assertEquals("PENDING", response.getStatus());
        assertEquals(30, response.getDurationSeconds());
        assertEquals(5, response.getLoadLevel());
        assertNotNull(response.getTestId());
    }
    
    @Test
    void testStartPerformanceTest() {
        // Given
        PerformanceTestRequest request = new PerformanceTestRequest(
                "Test Load", 1, 5, "Sample test"
        );
        PerformanceTestResponse createdTest = performanceTestService.createTest(request);
        
        // When
        PerformanceTestResponse startedTest = performanceTestService.startTest(createdTest.getTestId());
        
        // Then
        assertNotNull(startedTest);
        assertEquals("RUNNING", startedTest.getStatus());
        assertNotNull(startedTest.getStartTime());
    }
    
    @Test
    void testSimulateLoad() {
        // When
        PerformanceTestResponse response = performanceTestService.simulateLoad(10, 50);
        
        // Then
        assertNotNull(response);
        assertEquals("COMPLETED", response.getStatus());
        assertNotNull(response.getMetrics());
        assertEquals(10, response.getMetrics().getTotalRequests());
        assertTrue(response.getMetrics().getAverageResponseTime() > 0);
    }
    
    @Test
    void testGetTestResult_NotFound() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            performanceTestService.getTestResult("non-existent-id");
        });
    }
}
