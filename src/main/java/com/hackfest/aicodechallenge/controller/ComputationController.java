package com.hackfest.aicodechallenge.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hackfest.aicodechallenge.model.dto.ComputationResult;

@RestController
@RequestMapping("/compute")
public class ComputationController {

    @GetMapping("/inefficient/{n}")
    public ResponseEntity<ComputationResult> inefficientComputation(@PathVariable int n) {
        long startTime = System.currentTimeMillis();
        
        // Inefficient prime number calculation
        List<Integer> primes = new ArrayList();
        for (int i = 2; i <= n; i++) {
            boolean isPrime = true;
            for (int j = 2; j < i; j++) {  // Inefficient check
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                primes.add(i);
            }
        }

        // Fibonacci with recursion (O(2^n) time)
        long fib = fibonacci(n % 40); // Limit to prevent stack overflow

        // String concatenation in loop
        String concatenated = "";
        for (int i = 0; i < n; i++) {
            concatenated += i + ",";  // Creates new string each iteration
        }

        // Unnecessary matrix multiplication
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {  // O(n³) operation
                    matrix[i][j] += i * k + j * k;
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        return ResponseEntity.ok(
            new ComputationResult(primes.size(), fib, concatenated.length(), matrix[n-1][n-1], duration)
        );
    }

    // Inefficient recursive Fibonacci
    private long fibonacci(int n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}

