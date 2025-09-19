// 代码生成时间: 2025-09-19 23:53:26
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/performance")
@Service
public class PerformanceTestService {

    private static final String PERFORMANCE_TEST_URL = "http://localhost:8080/api/test";
    private final RestTemplate restTemplate;
    private final ExecutorService executorService;

    @Autowired
    public PerformanceTestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.executorService = Executors.newCachedThreadPool();
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> performTest(@RequestParam int numberOfRequests) throws InterruptedException {
        AtomicInteger responseCount = new AtomicInteger(0);
        Map<String, Object> results = new HashMap<>();

        for (int i = 0; i < numberOfRequests; i++) {
            executorService.submit(() -> {
                try {
                    ResponseEntity<String> response = restTemplate.getForEntity(PERFORMANCE_TEST_URL, String.class);
                    if (response.getStatusCode() == HttpStatus.OK) {
                        responseCount.incrementAndGet();
                    }
                } catch (Exception e) {
                    // Handle exception per request
                }
            });
        }

        // Wait for all tasks to complete
        executorService.shutdown();
        boolean finished = executorService.awaitTermination(60, TimeUnit.SECONDS);

        if (!finished) {
            throw new RuntimeException("Performance test did not finish within the expected time.");
        }

        results.put("successfulRequests", responseCount.get());
        results.put("totalRequests", numberOfRequests);

        return ResponseEntity.ok(results);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + ex.getMessage());
    }
}
