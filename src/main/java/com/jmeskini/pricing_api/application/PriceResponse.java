package com.jmeskini.pricing_api.application;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponse(
        long productId,
        long brandId,
        int priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency
) {
}
