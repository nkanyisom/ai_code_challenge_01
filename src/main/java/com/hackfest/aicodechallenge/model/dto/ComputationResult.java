package com.hackfest.aicodechallenge.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComputationResult {
    private int primeCount;
    private long fibonacci;
    private int concatenatedLength;
    private int matrixValue;
    private long computationTimeMs;

    // constructor, getters, setters...
}