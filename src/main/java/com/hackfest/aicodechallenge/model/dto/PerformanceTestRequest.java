package com.hackfest.aicodechallenge.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceTestRequest {
    
    @NotBlank(message = "Test name is required")
    private String testName;
    
    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    private Integer durationSeconds;
    
    @NotNull(message = "Load level is required")
    @Positive(message = "Load level must be positive")
    private Integer loadLevel;
    
    private String description;
}
