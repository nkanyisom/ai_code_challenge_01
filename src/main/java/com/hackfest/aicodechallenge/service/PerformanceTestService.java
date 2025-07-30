package com.hackfest.aicodechallenge.service;

import com.hackfest.aicodechallenge.model.dto.PerformanceTestRequest;
import com.hackfest.aicodechallenge.model.dto.PerformanceTestResponse;
import com.hackfest.aicodechallenge.model.entity.PerformanceTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class PerformanceTestService {
    
    private final Map<String, PerformanceTest> testStorage = new ConcurrentHashMap<>();
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
    private final Random random = new Random();
    
    public PerformanceTestResponse createTest(PerformanceTestRequest request) {
        log.info("Creating performance test: {}", request.getTestName());
        
        PerformanceTest test = new PerformanceTest(
                request.getTestName(),
                request.getDurationSeconds(),
                request.getLoadLevel(),
                request.getDescription()
        );
        
        testStorage.put(test.getId(), test);
        
        return mapToResponse(test);
    }
    
    public PerformanceTestResponse startTest(String testId) {
        log.info("Starting performance test: {}", testId);
        
        Optional<PerformanceTest> testOpt = Optional.ofNullable(testStorage.get(testId));
        if (testOpt.isEmpty()) {
            throw new IllegalArgumentException("Test not found: " + testId);
        }
        
        PerformanceTest test = testOpt.get();
        test.setStatus("RUNNING");
        test.setStartTime(LocalDateTime.now());
        
        // Simulate async test execution
        CompletableFuture.runAsync(() -> executeTest(test), executorService);
        
        return mapToResponse(test);
    }
    
    public PerformanceTestResponse getTestResult(String testId) {
        Optional<PerformanceTest> testOpt = Optional.ofNullable(testStorage.get(testId));
        if (testOpt.isEmpty()) {
            throw new IllegalArgumentException("Test not found: " + testId);
        }
        
        return mapToResponse(testOpt.get());
    }
    
    public List<PerformanceTestResponse> getAllTests() {
        return testStorage.values().stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    public void deleteTest(String testId) {
        log.info("Deleting performance test: {}", testId);
        if (!testStorage.containsKey(testId)) {
            throw new IllegalArgumentException("Test not found: " + testId);
        }
        testStorage.remove(testId);
    }
    
    public PerformanceTestResponse simulateLoad(int requests, int delayMs) {
        log.info("Simulating load with {} requests and {}ms delay", requests, delayMs);
        
        long startTime = System.currentTimeMillis();
        int successCount = 0;
        int errorCount = 0;
        double totalResponseTime = 0;
        double minResponseTime = Double.MAX_VALUE;
        double maxResponseTime = 0;
        
        for (int i = 0; i < requests; i++) {
            long requestStart = System.currentTimeMillis();
            
            try {
                // Simulate processing time
                Thread.sleep(delayMs + random.nextInt(50));
                
                // Simulate occasional errors (5% failure rate)
                if (random.nextDouble() < 0.05) {
                    errorCount++;
                } else {
                    successCount++;
                }
                
                long responseTime = System.currentTimeMillis() - requestStart;
                totalResponseTime += responseTime;
                minResponseTime = Math.min(minResponseTime, responseTime);
                maxResponseTime = Math.max(maxResponseTime, responseTime);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                errorCount++;
            }
        }
        
        long totalTime = System.currentTimeMillis() - startTime;
        double throughput = (double) requests / (totalTime / 1000.0);
        double avgResponseTime = totalResponseTime / requests;
        double errorRate = (double) errorCount / requests * 100;
        
        PerformanceTestResponse.PerformanceMetrics metrics = PerformanceTestResponse.PerformanceMetrics.builder()
                .averageResponseTime(avgResponseTime)
                .maxResponseTime(maxResponseTime)
                .minResponseTime(minResponseTime == Double.MAX_VALUE ? 0 : minResponseTime)
                .totalRequests(requests)
                .successfulRequests(successCount)
                .failedRequests(errorCount)
                .throughput(throughput)
                .errorRate(errorRate)
                .build();
        
        return PerformanceTestResponse.builder()
                .testId("load-simulation-" + System.currentTimeMillis())
                .testName("Load Simulation")
                .status("COMPLETED")
                .startTime(LocalDateTime.now().minusSeconds(totalTime / 1000))
                .endTime(LocalDateTime.now())
                .durationSeconds((int) (totalTime / 1000))
                .loadLevel(requests)
                .description("Simulated load test")
                .metrics(metrics)
                .build();
    }
    
    private void executeTest(PerformanceTest test) {
        try {
            log.info("Executing test: {}", test.getTestName());
            
            // Simulate test execution time
            Thread.sleep(test.getDurationSeconds() * 1000L);
            
            // Generate mock performance metrics
            generateMockMetrics(test);
            
            test.setStatus("COMPLETED");
            test.setEndTime(LocalDateTime.now());
            
            log.info("Test completed: {}", test.getTestName());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            test.setStatus("FAILED");
            test.setEndTime(LocalDateTime.now());
            log.error("Test interrupted: {}", test.getTestName(), e);
        } catch (Exception e) {
            test.setStatus("FAILED");
            test.setEndTime(LocalDateTime.now());
            log.error("Test failed: {}", test.getTestName(), e);
        }
    }
    
    private void generateMockMetrics(PerformanceTest test) {
        // Generate realistic mock metrics based on load level
        int baseRequests = test.getLoadLevel() * test.getDurationSeconds();
        test.setTotalRequests(baseRequests);
        
        // Simulate 95% success rate
        test.setSuccessfulRequests((int) (baseRequests * 0.95));
        test.setFailedRequests(baseRequests - test.getSuccessfulRequests());
        
        // Generate response times (in milliseconds)
        test.setAverageResponseTime(100.0 + random.nextDouble() * 200);
        test.setMinResponseTime(50.0 + random.nextDouble() * 50);
        test.setMaxResponseTime(test.getAverageResponseTime() + random.nextDouble() * 500);
        
        // Calculate throughput (requests per second)
        test.setThroughput((double) baseRequests / test.getDurationSeconds());
        
        // Calculate error rate
        test.setErrorRate((double) test.getFailedRequests() / test.getTotalRequests() * 100);
    }
    
    private PerformanceTestResponse mapToResponse(PerformanceTest test) {
        PerformanceTestResponse.PerformanceMetrics metrics = null;
        
        if (test.getTotalRequests() != null) {
            metrics = PerformanceTestResponse.PerformanceMetrics.builder()
                    .averageResponseTime(test.getAverageResponseTime())
                    .maxResponseTime(test.getMaxResponseTime())
                    .minResponseTime(test.getMinResponseTime())
                    .totalRequests(test.getTotalRequests())
                    .successfulRequests(test.getSuccessfulRequests())
                    .failedRequests(test.getFailedRequests())
                    .throughput(test.getThroughput())
                    .errorRate(test.getErrorRate())
                    .build();
        }
        
        return PerformanceTestResponse.builder()
                .testId(test.getId())
                .testName(test.getTestName())
                .status(test.getStatus())
                .startTime(test.getStartTime())
                .endTime(test.getEndTime())
                .durationSeconds(test.getDurationSeconds())
                .loadLevel(test.getLoadLevel())
                .description(test.getDescription())
                .metrics(metrics)
                .build();
    }
}
