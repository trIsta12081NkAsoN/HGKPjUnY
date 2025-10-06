// 代码生成时间: 2025-10-07 03:54:24
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
# TODO: 优化性能
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.FilterConfig;
import org.springframework.core.annotation.AliasFor;
import org.springframework.context.annotation.Lazy;
# TODO: 优化性能
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;

/**
 * Spring Boot component to prevent XSS attacks.
 * It uses the OncePerRequestFilter to clean input from requests.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // Ensure this filter runs before others
public class XssProtectionComponent extends OncePerRequestFilter {

    private static final Set<String> SAFE_CONTENT_TYPES = Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("text/plain")));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Skip filter if the request content type is safe
        String contentType = request.getContentType();
# TODO: 优化性能
        if (contentType != null && SAFE_CONTENT_TYPES.contains(contentType.split(";")[0].trim())) {
            filterChain.doFilter(request, response);
            return;
        }

        // Clean request parameters to prevent XSS attack
        HttpServletRequest cleanRequest = new InputCleaningRequestWrapper(request);
        filterChain.doFilter(cleanRequest, response);
    }

    // Exception handler for XSS request
    @ControllerAdvice
    public class XssExceptionHandler {

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<String> handleXssException(IllegalArgumentException e) {
            return new ResponseEntity<>("XSS attack detected and blocked.", HttpStatus.BAD_REQUEST);
        }
# 增强安全性
    }
}

// Inner class to clean the input parameters
class InputCleaningRequestWrapper extends HttpServletRequestWrapper {

    public InputCleaningRequestWrapper(HttpServletRequest request) {
# 添加错误处理
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                values[i] = cleanXss(values[i]);
            }
# TODO: 优化性能
        }
        return values;
    }

    private String cleanXss(String value) {
        // Here you can implement your own logic to sanitize input values
        // Remove script tags, onerror handlers, etc.
        // For simplicity, this example only escapes HTML entities.
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
                .replaceAll(""", "&quot;").replaceAll("'", "&#x27;").replaceAll("(?i)script", "");
# TODO: 优化性能
        return value;
    }
}
