package com.jmeskini.pricing_api.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceSelectorTest {

    @Test
    @DisplayName("Chooses the highest priority price when ranges overlap")
    void selectsHighestPriorityWhenOverlapping() {
        Price base = new Price(
                1L,
                LocalDateTime.of(2020, 6, 14, 0, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                1,
                35455L,
                0,
                new BigDecimal("35.50"),
                "EUR"
        );
        Price promo = new Price(
                1L,
                LocalDateTime.of(2020, 6, 14, 15, 0, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30, 0),
                2,
                35455L,
                1,
                new BigDecimal("25.45"),
                "EUR"
        );

        PriceSelector selector = new PriceSelector();
        assertThat(selector.selectApplicable(List.of(base, promo)))
                .contains(promo);
    }

    @Test
    @DisplayName("Returns the highest priority price from already-filtered candidates")
    void returnsHighestPriorityFromFilteredCandidates() {
        Price price = new Price(
                1L,
                LocalDateTime.of(2020, 6, 14, 0, 0, 0),
                LocalDateTime.of(2020, 6, 14, 1, 0, 0),
                1,
                35455L,
                0,
                new BigDecimal("35.50"),
                "EUR"
        );
        Price higherPriority = new Price(
                1L,
                LocalDateTime.of(2020, 6, 14, 0, 0, 0),
                LocalDateTime.of(2020, 6, 14, 1, 0, 0),
                2,
                35455L,
                1,
                new BigDecimal("30.50"),
                "EUR"
        );

        PriceSelector selector = new PriceSelector();
        assertThat(selector.selectApplicable(List.of(price, higherPriority)))
                .contains(higherPriority);
    }
}
