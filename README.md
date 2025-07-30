# AI Code Challenge - Spring Boot Performance Testing Application

This is a Spring Boot application built with Java 17 that provides REST APIs for performance testing with built-in metrics and monitoring capabilities.

## Features

- **Java 17** - Latest LTS version of Java
- **Spring Web** - RESTful web services
- **Spring Boot Actuator** - Production-ready metrics and monitoring
- **Lombok** - Reduces boilerplate code
- **Performance Testing APIs** - Create, start, monitor, and analyze performance tests
- **Load Simulation** - Built-in load testing capabilities
- **Comprehensive Metrics** - Response times, throughput, error rates
- **Global Exception Handling** - Centralized error management
- **CORS Support** - Cross-origin resource sharing enabled

## Project Structure

```
src/
├── main/
│   ├── java/com/hackfest/aicodechallenge/
│   │   ├── config/                    # Configuration classes
│   │   │   ├── ApplicationConfig.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── controller/                # REST Controllers
│   │   │   └── PerformanceTestController.java
│   │   ├── model/
│   │   │   ├── dto/                   # Data Transfer Objects
│   │   │   │   ├── ApiResponse.java
│   │   │   │   ├── PerformanceTestRequest.java
│   │   │   │   └── PerformanceTestResponse.java
│   │   │   └── entity/                # Domain Entities
│   │   │       └── PerformanceTest.java
│   │   ├── service/                   # Business Logic
│   │   │   └── PerformanceTestService.java
│   │   └── AiCodeChallengeApplication.java
│   └── resources/
│       └── application.properties     # Application configuration
└── test/                             # Unit tests
    └── java/com/hackfest/aicodechallenge/
        ├── controller/
        └── service/
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

### Example 1: Quick Load Test
```bash
curl -X POST "http://localhost:8080/api/v1/performance/load-test?requests=50&delayMs=100"
```

### Example 2: Create and Run Comprehensive Test
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

## Technologies Used

- **Spring Boot 3.2.0** - Application framework
- **Java 17** - Programming language
- **Maven** - Build tool
- **Lombok** - Code generation
- **JUnit 5** - Testing framework
- **Jackson** - JSON processing
- **SLF4J** - Logging facade

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
