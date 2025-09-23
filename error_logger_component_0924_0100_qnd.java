// 代码生成时间: 2025-09-24 01:00:08
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Error Logger Component handles exceptions and logs errors.
 */
@ControllerAdvice
@Component
public class ErrorLoggerComponent extends ResponseEntityExceptionHandler {

    /**
     * Handles any generic exception and logs the error.
     *
     * @param ex Exception that was thrown.
     * @param request The current HTTP request.
     * @return A response entity containing the error details.
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, Object> handleGenericException(Exception ex, HttpServletRequest request) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("timestamp", new Date());
        errorMap.put("status", request.getStatus());
        errorMap.put("error", "Error");
        errorMap.put("exception", ex.getClass().getName());
        errorMap.put("message", ex.getMessage());
        errorMap.put("path", request.getRequestURI());
        // Log the error details here, e.g., using a logging framework like Log4j or SLF4J.
        // log.error("Exception occurred: " + ex.getMessage(), ex);

        return errorMap;
    }

    /**
     * Handles ResourceNotFoundException and logs the error.
     *
     * @param ex ResourceNotFoundException that was thrown.
     * @param request The current HTTP request.
     * @return A response entity containing the error details.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public Map<String, Object> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("timestamp", new Date());
        errorMap.put("status", 404);
        errorMap.put("error", "Not Found");
        errorMap.put("message", ex.getMessage());
        errorMap.put("path", request.getRequestURI());
        // Log the error details here.
        // log.error("Resource not found: " + ex.getMessage(), ex);

        return errorMap;
    }

    // Custom exception class for resource not found.
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
