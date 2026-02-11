package com.jmeskini.pricing_api.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.jmeskini.pricing_api.domain.Price;
import com.jmeskini.pricing_api.domain.PriceRepository;
import com.jmeskini.pricing_api.domain.PriceSelector;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FindApplicablePriceServiceTest {

    @Test
    @DisplayName("Returns the matching price for the requested application date")
    void returnsPriceResponseForMatchingDate() {
        PriceRepository repository = (productId, brandId, applicationDate) -> List.of(
                new Price(
                        brandId,
                        LocalDateTime.of(2020, 6, 15, 0, 0, 0),
                        LocalDateTime.of(2020, 6, 15, 11, 0, 0),
                        3,
                        productId,
                        1,
                        new BigDecimal("30.50"),
                        "EUR"
                )
        );

        FindApplicablePriceService service = new FindApplicablePriceService(repository, new PriceSelector());

        PriceResponse response = service.execute(new GetPriceQuery(
                LocalDateTime.of(2020, 6, 15, 10, 0, 0),
                35455L,
                1L
        ));

        assertThat(response.priceList()).isEqualTo(3);
        assertThat(response.price()).isEqualByComparingTo(new BigDecimal("30.50"));
    }
}
