package com.jmeskini.pricing_api.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PriceSelector {

    public Optional<Price> selectApplicable(List<Price> prices) {
        return prices.stream()
                .max(Comparator.comparingInt(Price::getPriority)
                        .thenComparingInt(Price::getPriceList));
    }
}
