package com.hackfest.aicodechallenge.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceTestResponse {
    
    private String testId;
    private String testName;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer durationSeconds;
    private Integer loadLevel;
    private String description;
    private PerformanceMetrics metrics;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PerformanceMetrics {
        private Double averageResponseTime;
        private Double maxResponseTime;
        private Double minResponseTime;
        private Integer totalRequests;
        private Integer successfulRequests;
        private Integer failedRequests;
        private Double throughput;
        private Double errorRate;
    }
}
