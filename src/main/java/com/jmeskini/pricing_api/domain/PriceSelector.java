package com.jmeskini.pricing_api.domain;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PriceSelector {

    public Optional<Price> selectApplicable(List<Price> prices, LocalDateTime applicationDate) {
        return prices.stream()
                .filter(price -> price.appliesAt(applicationDate))
                .max(Comparator.comparingInt(Price::getPriority)
                        .thenComparingInt(Price::getPriceList));
    }
}
