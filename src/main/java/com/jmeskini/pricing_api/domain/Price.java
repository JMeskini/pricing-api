package com.jmeskini.pricing_api.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Price {
    private final long brandId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final int priceList;
    private final long productId;
    private final int priority;
    private final BigDecimal price;
    private final String currency;

    public boolean appliesAt(LocalDateTime dateTime) {
        return (dateTime.isEqual(startDate) || dateTime.isAfter(startDate))
                && (dateTime.isEqual(endDate) || dateTime.isBefore(endDate));
    }
}
