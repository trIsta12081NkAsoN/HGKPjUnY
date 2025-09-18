// 代码生成时间: 2025-09-19 02:29:22
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

// 引入异常处理注解
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ReactiveService {

    // 响应式GET请求
    @GetMapping("/greeting")
    public Mono<ResponseEntity<String>> getGreeting() {
        return Mono.just(
                ResponseEntity.ok("Hello, Reactive World!")
        );
    }

    // 异常处理
    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<String>> handleRuntimeException(RuntimeException ex, ServerWebExchange exchange) {
        return Mono.just(
                new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)
        );
    }

    // 可以添加更多响应式接口和异常处理方法
}
