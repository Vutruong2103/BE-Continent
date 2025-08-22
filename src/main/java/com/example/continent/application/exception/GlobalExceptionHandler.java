package com.example.continent.application.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException ex, Locale locale) {
        var problem = ex.getProblemDetail();

        // dịch message từ messages.properties
        String localizedMessage = messageSource.getMessage(
                ex.getMessage(),   // code
                null,              // arguments
                ex.getMessage(),   // defaultMessage
                locale
        );
        problem.setDetail(localizedMessage);

        return ResponseEntity.status(problem.getStatus()).body(problem);
    }
}