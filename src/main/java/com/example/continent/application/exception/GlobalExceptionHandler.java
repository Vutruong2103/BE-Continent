package com.example.continent.application.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @RestControllerAdvice bắt exception
 * @ExceptionHandler chỉ ra lớp cần bắt
 * <p>
 * handleBaseException:
 * Khi service hoặc controller ném throw new BaseException(...) → Spring Boot sẽ gọi method này.
 * <p>
 * handleValidException: trả về Validation Failed
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException ex, Locale locale) {
        var problem = ex.getProblemDetail();
        String localizedMessage = messageSource.getMessage(
                ex.getMessage(),
                null,
                ex.getMessage(),
                locale
        );
        problem.setDetail(localizedMessage);
        return ResponseEntity.status(problem.getStatus()).body(problem);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("error", "Validation Failed");
        List<Map<String, String>> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("field", err.getField());
                    map.put("message", err.getDefaultMessage());
                    return map;
                })
                .toList();
        errors.put("errors", fieldErrors);
        return ResponseEntity.badRequest().body(errors);
    }
}