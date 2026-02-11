package com.jmeskini.pricing_api.infrastructure.web;

import com.jmeskini.pricing_api.domain.PriceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(PriceNotFoundException ex, HttpServletRequest request) {
        log.warn("Price not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError("NOT_FOUND", ex.getMessage(), request.getRequestURI(), List.of()));
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex, HttpServletRequest request) {
        log.warn("Bad request: {}", ex.getMessage());
        List<ErrorResponse.FieldError> details = switch (ex) {
            case MissingServletRequestParameterException missing -> List.of(
                    new ErrorResponse.FieldError(missing.getParameterName(), "is required")
            );
            case MethodArgumentTypeMismatchException mismatch -> List.of(
                    new ErrorResponse.FieldError(mismatch.getName(), "has invalid format")
            );
            case ConstraintViolationException violation -> violation.getConstraintViolations().stream()
                    .map(this::toFieldError)
                    .toList();
            default -> List.of();
        };
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError("BAD_REQUEST", "Invalid request parameters", request.getRequestURI(), details));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError("INTERNAL_ERROR", "Unexpected error", request.getRequestURI(), List.of()));
    }

    private ErrorResponse buildError(String code, String message, String path, List<ErrorResponse.FieldError> details) {
        return new ErrorResponse(code, message, path, LocalDateTime.now(), details);
    }

    private ErrorResponse.FieldError toFieldError(ConstraintViolation<?> violation) {
        String field = Optional.ofNullable(violation.getPropertyPath())
                .map(Object::toString)
                .orElse("param");
        return new ErrorResponse.FieldError(field, violation.getMessage());
    }
}
