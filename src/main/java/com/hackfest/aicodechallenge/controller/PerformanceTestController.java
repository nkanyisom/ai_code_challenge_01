package com.hackfest.aicodechallenge.controller;

import com.hackfest.aicodechallenge.model.dto.ApiResponse;
import com.hackfest.aicodechallenge.model.dto.PerformanceTestRequest;
import com.hackfest.aicodechallenge.model.dto.PerformanceTestResponse;
import com.hackfest.aicodechallenge.service.PerformanceTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/performance")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PerformanceTestController {
    
    private final PerformanceTestService performanceTestService;
    
    @PostMapping("/tests")
    public ResponseEntity<ApiResponse<PerformanceTestResponse>> createTest(
            @Valid @RequestBody PerformanceTestRequest request) {
        
        log.info("Creating performance test: {}", request.getTestName());
        
        try {
            PerformanceTestResponse response = performanceTestService.createTest(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(response, "Performance test created successfully"));
        } catch (Exception e) {
            log.error("Error creating test", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create test: " + e.getMessage()));
        }
    }
    
    @PostMapping("/tests/{testId}/start")
    public ResponseEntity<ApiResponse<PerformanceTestResponse>> startTest(
            @PathVariable @NotBlank String testId) {
        
        log.info("Starting performance test: {}", testId);
        
        try {
            PerformanceTestResponse response = performanceTestService.startTest(testId);
            return ResponseEntity.ok(ApiResponse.success(response, "Performance test started"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            log.error("Error starting test: {}", testId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to start test: " + e.getMessage()));
        }
    }
    
    @GetMapping("/tests/{testId}")
    public ResponseEntity<ApiResponse<PerformanceTestResponse>> getTestResult(
            @PathVariable @NotBlank String testId) {
        
        log.info("Getting test result: {}", testId);
        
        try {
            PerformanceTestResponse response = performanceTestService.getTestResult(testId);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            log.error("Error getting test result: {}", testId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to get test result: " + e.getMessage()));
        }
    }
    
    @GetMapping("/tests")
    public ResponseEntity<ApiResponse<List<PerformanceTestResponse>>> getAllTests() {
        
        log.info("Getting all performance tests");
        
        try {
            List<PerformanceTestResponse> tests = performanceTestService.getAllTests();
            return ResponseEntity.ok(ApiResponse.success(tests, "Retrieved all tests"));
        } catch (Exception e) {
            log.error("Error getting all tests", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to get tests: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/tests/{testId}")
    public ResponseEntity<ApiResponse<Void>> deleteTest(
            @PathVariable @NotBlank String testId) {
        
        log.info("Deleting performance test: {}", testId);
        
        try {
            performanceTestService.deleteTest(testId);
            return ResponseEntity.ok(ApiResponse.success(null, "Test deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            log.error("Error deleting test: {}", testId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete test: " + e.getMessage()));
        }
    }
    
    @PostMapping("/load-test")
    public ResponseEntity<ApiResponse<PerformanceTestResponse>> simulateLoad(
            @RequestParam @Min(1) @Max(10000) int requests,
            @RequestParam @Min(0) @Max(5000) int delayMs) {
        
        log.info("Simulating load with {} requests and {}ms delay", requests, delayMs);
        
        try {
            PerformanceTestResponse response = performanceTestService.simulateLoad(requests, delayMs);
            return ResponseEntity.ok(ApiResponse.success(response, "Load simulation completed"));
        } catch (Exception e) {
            log.error("Error simulating load", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to simulate load: " + e.getMessage()));
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("OK", "Service is healthy"));
    }
}
