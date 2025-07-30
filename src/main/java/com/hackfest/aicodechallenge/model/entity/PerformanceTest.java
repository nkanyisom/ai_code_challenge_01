package com.hackfest.aicodechallenge.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceTest {
    
    private String id;
    private String testName;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer durationSeconds;
    private Integer loadLevel;
    private String description;
    private Double averageResponseTime;
    private Double maxResponseTime;
    private Double minResponseTime;
    private Integer totalRequests;
    private Integer successfulRequests;
    private Integer failedRequests;
    private Double throughput;
    private Double errorRate;
    
    public PerformanceTest(String testName, Integer durationSeconds, Integer loadLevel, String description) {
        this.id = UUID.randomUUID().toString();
        this.testName = testName;
        this.durationSeconds = durationSeconds;
        this.loadLevel = loadLevel;
        this.description = description;
        this.status = "PENDING";
        this.startTime = LocalDateTime.now();
    }
}
