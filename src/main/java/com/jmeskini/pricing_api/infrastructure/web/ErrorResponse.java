package com.jmeskini.pricing_api.infrastructure.web;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        String code,
        String message,
        String path,
        LocalDateTime timestamp,
        List<FieldError> details
) {
    public record FieldError(String field, String message) {
    }
}
