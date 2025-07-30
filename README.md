# AI Code Challenge - Spring Boot Performance Testing Application

This is a comprehensive Spring Boot application built with Java 17 that provides REST APIs for performance testing, computational workload simulation, and system monitoring with built-in metrics and real-time performance analysis.

## Features

- **Java 17** - Latest LTS version of Java
- **Spring Web** - RESTful web services
- **Spring Boot Actuator** - Production-ready metrics and monitoring
- **Lombok** - Reduces boilerplate code
- **Performance Testing APIs** - Create, start, monitor, and analyze performance tests
- **Computational Workload Controller** - Intentionally inefficient algorithms for performance testing
- **Load Simulation** - Built-in load testing capabilities with configurable parameters
- **Comprehensive Metrics** - Response times, throughput, error rates, computation times
- **Global Exception Handling** - Centralized error management
- **CORS Support** - Cross-origin resource sharing enabled
- **Postman Collection** - Complete API testing suite included

## Project Structure

```
src/
├── main/
│   ├── java/com/hackfest/aicodechallenge/
│   │   ├── config/                    # Configuration classes
│   │   │   ├── ApplicationConfig.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── controller/                # REST Controllers
│   │   │   ├── ComputationController.java      # NEW: Inefficient algorithms for testing
│   │   │   └── PerformanceTestController.java
│   │   ├── model/
│   │   │   ├── dto/                   # Data Transfer Objects
│   │   │   │   ├── ApiResponse.java
│   │   │   │   ├── ComputationResult.java      # NEW: Computation response DTO
│   │   │   │   ├── PerformanceTestRequest.java
│   │   │   │   └── PerformanceTestResponse.java
│   │   │   └── entity/                # Domain Entities
│   │   │       └── PerformanceTest.java
│   │   ├── service/                   # Business Logic
│   │   │   ├── ComputationService.java         # NEW: Computational algorithms
│   │   │   └── PerformanceTestService.java
│   │   └── AiCodeChallengeApplication.java
│   └── resources/
│       └── application.properties     # Application configuration
├── test/                             # Unit tests
│   └── java/com/hackfest/aicodechallenge/
│       ├── controller/
│       └── service/
├── AI_Code_Challenge_Postman_Collection.json  # NEW: Complete API testing suite
├── README.md
└── pom.xml
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd ai_code_challenge_01
   ```

2. **Build the project:**
   ```bash
   mvn clean compile
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Verify the application is running:**
   - Application: http://localhost:8080
   - Health check: http://localhost:8080/api/v1/performance/health
   - Actuator endpoints: http://localhost:8080/actuator

## API Documentation

### Computation Controller (NEW!)

The ComputationController provides intentionally inefficient algorithms designed to create computational load for performance testing purposes.

#### Inefficient Computation Endpoint
```http
GET /compute/inefficient/{n}
```

**Parameters:**
- `n` (path parameter): Integer value for computation complexity (1-10000)

**Response:**
```json
{
    "primeCount": 25,
    "fibonacci": 6765,
    "concatenatedLength": 290,
    "matrixValue": 980100,
    "computationTimeMs": 45
}
```

**Performance Examples:**
- `n=10`: ~0ms (4 primes, fibonacci=55)
- `n=100`: ~5ms (25 primes, fibonacci=6765) 
- `n=500`: ~50ms+ (significant computation time)
- `n=1000`: ~200ms+ (stress test level)

### Performance Test Endpoints

#### 1. Create Performance Test
```http
POST /api/v1/performance/tests
Content-Type: application/json

{
    "testName": "Load Test 1",
    "durationSeconds": 60,
    "loadLevel": 10,
    "description": "Sample performance test"
}
```

#### 2. Start Performance Test
```http
POST /api/v1/performance/tests/{testId}/start
```

#### 3. Get Test Result
```http
GET /api/v1/performance/tests/{testId}
```

#### 4. Get All Tests
```http
GET /api/v1/performance/tests
```

#### 5. Delete Test
```http
DELETE /api/v1/performance/tests/{testId}
```

#### 6. Simulate Load (Immediate)
```http
POST /api/v1/performance/load-test?requests=100&delayMs=50
```

#### 7. Health Check
```http
GET /api/v1/performance/health
```

### Response Format

All API responses follow this structure:
```json
{
    "success": true,
    "message": "Operation completed successfully",
    "data": { ... },
    "timestamp": "2025-01-30T10:15:30",
    "requestId": null
}
```

### Performance Metrics

The application provides comprehensive metrics including:
- **Response Times**: Average, minimum, maximum
- **Throughput**: Requests per second
- **Success Rate**: Percentage of successful requests
- **Error Rate**: Percentage of failed requests
- **Request Counts**: Total, successful, failed requests

## Monitoring and Actuator Endpoints

Spring Boot Actuator provides several monitoring endpoints:

- `/actuator/health` - Application health status
- `/actuator/metrics` - Application metrics
- `/actuator/info` - Application information
- `/actuator/env` - Environment properties
- `/actuator/beans` - Spring beans information
- `/actuator/mappings` - Request mappings

## Configuration

Key configuration properties in `application.properties`:

```properties
# Server Configuration
server.port=8080

# Actuator Configuration
management.endpoints.web.exposure.include=health,info,metrics,prometheus,env,beans,mappings
management.endpoint.health.show-details=always

# Logging Configuration
logging.level.com.hackfest.aicodechallenge=INFO

# Thread Pool Configuration
spring.task.execution.pool.core-size=5
spring.task.execution.pool.max-size=20
```

## Testing

Run the unit tests:
```bash
mvn test
```

Run with coverage:
```bash
mvn test jacoco:report
```

## Performance Testing Examples

### Example 1: Test Computational Load
```bash
# Test small computation (fast)
curl http://localhost:8080/compute/inefficient/10

# Test medium computation 
curl http://localhost:8080/compute/inefficient/100

# Test large computation (slow)
curl http://localhost:8080/compute/inefficient/500

# Stress test (very slow)
curl http://localhost:8080/compute/inefficient/1000
```

### Example 2: Quick Load Test
```bash
curl -X POST "http://localhost:8080/api/v1/performance/load-test?requests=50&delayMs=100"
```

### Example 3: Create and Run Comprehensive Test
```bash
# 1. Create test
curl -X POST http://localhost:8080/api/v1/performance/tests \
  -H "Content-Type: application/json" \
  -d '{
    "testName": "API Load Test",
    "durationSeconds": 30,
    "loadLevel": 15,
    "description": "Testing API under moderate load"
  }'

# 2. Start test (use testId from previous response)
curl -X POST http://localhost:8080/api/v1/performance/tests/{testId}/start

# 3. Check results
curl http://localhost:8080/api/v1/performance/tests/{testId}
```

## Postman Collection

A comprehensive Postman collection (`AI_Code_Challenge_Postman_Collection.json`) is included with:

### 🧮 **Computation Controller Tests**
- **Small Load Test** (n=10): Quick validation test
- **Medium Load Test** (n=100): Moderate performance test  
- **Large Load Test** (n=500): Performance degradation test
- **Stress Test** (n=1000): Maximum load test

### 📊 **Performance Test API Suite**
- **Create Performance Test**: Test creation with validation
- **Start Performance Test**: Test execution initiation
- **Get Test Result**: Results retrieval with metrics
- **Get All Tests**: Complete test listing
- **Load Simulation**: Quick (50 req) and Stress (500 req) tests
- **Health Check**: Service availability validation
- **Delete Test**: Cleanup operations

### 🔍 **Spring Boot Actuator Monitoring**
- **Application Health**: Overall system health
- **Application Metrics**: Comprehensive metrics overview
- **JVM Memory Metrics**: Memory usage monitoring
- **HTTP Request Metrics**: Request performance tracking

### How to Use Postman Collection:
1. Import `AI_Code_Challenge_Postman_Collection.json` into Postman
2. Set base URL to `http://localhost:8080` (already configured)
3. Run individual tests or use Collection Runner for full suite
4. Monitor variables like `{{testId}}` for workflow automation
5. Review test scripts for automated validation

## Real-World Testing Scenarios

### Scenario 1: CPU-Intensive Workload Testing
```bash
# Start with baseline
curl http://localhost:8080/compute/inefficient/10

# Gradually increase load
curl http://localhost:8080/compute/inefficient/50
curl http://localhost:8080/compute/inefficient/100
curl http://localhost:8080/compute/inefficient/200
curl http://localhost:8080/compute/inefficient/500

# Monitor JVM metrics during testing
curl http://localhost:8080/actuator/metrics/jvm.memory.used
curl http://localhost:8080/actuator/metrics/system.cpu.usage
```

### Scenario 2: Combined Load Testing
```bash
# Create multiple performance tests while running computation
curl -X POST http://localhost:8080/api/v1/performance/tests \
  -H "Content-Type: application/json" \
  -d '{"testName": "High Load Test", "durationSeconds": 60, "loadLevel": 20, "description": "Testing under high computational load"}'

# Start computation load in parallel
curl http://localhost:8080/compute/inefficient/300 &
curl http://localhost:8080/compute/inefficient/400 &

# Run load simulation
curl -X POST "http://localhost:8080/api/v1/performance/load-test?requests=200&delayMs=75"
```

## Technologies Used

- **Spring Boot 3.2.0** - Application framework
- **Java 17** - Programming language  
- **Maven** - Build tool
- **Lombok** - Code generation
- **JUnit 5** - Testing framework
- **Jackson** - JSON processing
- **SLF4J** - Logging facade
- **Spring Boot Actuator** - Production monitoring

## Architecture Highlights

### Computation Service Features:
- **Inefficient Prime Counting**: O(n²) algorithm for testing CPU load
- **Fibonacci Calculation**: Recursive implementation with high complexity
- **String Concatenation**: Memory-intensive operations
- **Matrix Operations**: CPU-intensive mathematical computations
- **Performance Timing**: Built-in execution time measurement

### Performance Testing Features:
- **Async Test Execution**: Non-blocking test execution
- **Comprehensive Metrics**: Response times, throughput, error rates
- **Configurable Load Levels**: Adjustable request volumes and delays
- **Real-time Monitoring**: Live test status and progress tracking
- **Historical Data**: Test result storage and retrieval

### Monitoring & Observability:
- **Health Checks**: Application and component health status
- **Metrics Collection**: JVM, HTTP, custom application metrics
- **Performance Tracking**: Request/response times and patterns
- **Resource Monitoring**: Memory, CPU, and system resource usage

## Development Notes

### For Performance Testing:
- Start with small `n` values (10-50) for computation tests
- Gradually increase to identify performance bottlenecks
- Use Actuator endpoints to monitor resource usage
- Combine computation load with API load testing for realistic scenarios

### For Production Deployment:
- Configure appropriate logging levels
- Enable only necessary Actuator endpoints
- Set proper CORS policies for security
- Monitor JVM heap size under load
- Consider implementing caching for production algorithms

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-algorithm`)
3. Add your inefficient algorithms to ComputationService
4. Include corresponding tests and Postman requests
5. Update documentation with performance characteristics
6. Submit a pull request with benchmark results

## License

This project is licensed under the MIT License - see the LICENSE file for details.

---

**Note**: The algorithms in ComputationController are intentionally inefficient for educational and testing purposes. Do not use these implementations in production systems!
