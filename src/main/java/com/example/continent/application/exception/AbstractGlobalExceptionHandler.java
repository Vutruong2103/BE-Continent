package com.example.continent.application.exception;

import com.example.continent.application.payload.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class AbstractGlobalExceptionHandler extends ResponseEntityExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.warn("Unauthorized error: {}", authException.getMessage());
        writeProblemDetailResponse(request, response, authException, HttpStatus.UNAUTHORIZED, "error.unauthorized");
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.warn("Access denied: {}", accessDeniedException.getMessage());
        writeProblemDetailResponse(request, response, accessDeniedException, HttpStatus.FORBIDDEN, "error.forbidden");
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse<ProblemDetail>> handleForbiddenException(ForbiddenException ex, WebRequest request) {
        log.warn("Forbidden access attempt: {}", ex.getMessage());
        return buildProblemDetailResponse(ex, HttpStatus.FORBIDDEN, request, "error.forbidden");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<ProblemDetail>> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return buildProblemDetailResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<ProblemDetail>> handleBadRequestException(BadRequestException ex, WebRequest request) {
        return buildProblemDetailResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<ProblemDetail>> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        log.warn("Bad request received: {}", ex.getMessage());
        return buildProblemDetailResponse(ex, HttpStatus.BAD_REQUEST, request, "error.illegalArgument");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<ProblemDetail>> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        log.warn("Request conflicted with current state: {}", ex.getMessage());
        return buildProblemDetailResponse(ex, HttpStatus.CONFLICT, request, "error.conflict");
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<ProblemDetail>> handleConflictException(ConflictException ex, WebRequest request) {
        return buildProblemDetailResponse(ex, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(MembershipValidationException.class)
    public ResponseEntity<ApiResponse<ProblemDetail>> handleMembershipValidationException(MembershipValidationException ex, WebRequest request) {
        log.warn("Membership validation error: {}", ex.getMessage());
        return buildProblemDetailResponse(ex, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ProblemDetail>> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("An unhandled exception occurred: ", ex);
        return buildProblemDetailResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request, "error.internalServerError");
    }

    private void writeProblemDetailResponse(HttpServletRequest request, HttpServletResponse response, Exception ex, HttpStatus status, String messageCode) throws IOException {
        String message = resolveMessage(messageCode, null, ex.getMessage(), request.getLocale());
        ProblemDetail problemDetail = createProblemDetail(ex, status, request.getRequestURI(), message);
        ApiResponse<ProblemDetail> apiResponse = buildApiResponse(problemDetail);

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }

    private ResponseEntity<ApiResponse<ProblemDetail>> buildProblemDetailResponse(Exception ex, HttpStatus status, WebRequest request, String... messageCode) {
        String message = resolveMessage(messageCode.length > 0 ? messageCode[0] : "", null, ex.getMessage(), request.getLocale());
        String path = request.getDescription(false).substring(4);
        ProblemDetail problemDetail = createProblemDetail(ex, status, path, message);
        ApiResponse<ProblemDetail> apiResponse = buildApiResponse(problemDetail);

        return new ResponseEntity<>(apiResponse, new HttpHeaders(), status);
    }

    private String resolveMessage(String code, Object[] args, String defaultMessage, java.util.Locale locale) {
        if (code == null || code.isEmpty()) {
            return defaultMessage;
        }
        try {
            return messageSource.getMessage(code, args, locale);
        } catch (NoSuchMessageException e) {
            log.trace("Could not find message for code '{}'. Using default message.", code);
            return defaultMessage;
        }
    }

    private ProblemDetail createProblemDetail(Exception ex, HttpStatus status, String path, String detail) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setDetail(detail);
        problemDetail.setTitle(status.getReasonPhrase());
        problemDetail.setInstance(java.net.URI.create(path));
        return problemDetail;
    }

    private ApiResponse<ProblemDetail> buildApiResponse(ProblemDetail problemDetail) {
        return ApiResponse.<ProblemDetail>builder()
                .status(problemDetail.getStatus())
                .message(problemDetail.getDetail())
                .path(problemDetail.getInstance().toString())
                .data(problemDetail)
                .build();
    }
}
