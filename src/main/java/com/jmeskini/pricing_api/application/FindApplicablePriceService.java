package com.jmeskini.pricing_api.application;

import com.jmeskini.pricing_api.domain.Price;
import com.jmeskini.pricing_api.domain.PriceNotFoundException;
import com.jmeskini.pricing_api.domain.PriceRepository;
import com.jmeskini.pricing_api.domain.PriceSelector;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class FindApplicablePriceService implements FindApplicablePriceUseCase {
    private final PriceRepository priceRepository;
    private final PriceSelector priceSelector;

    @Override
    public PriceResponse execute(GetPriceQuery query) {
        List<Price> prices = priceRepository.findByProductAndBrandAndDate(
                query.productId(),
                query.brandId(),
                query.applicationDate()
        );
        log.debug("Candidate prices found count={} for productId={} brandId={}", prices.size(), query.productId(), query.brandId());
        Price selected = priceSelector.selectApplicable(prices)
                .orElseThrow(() -> new PriceNotFoundException(query.productId(), query.brandId()));

        return new PriceResponse(
                selected.getProductId(),
                selected.getBrandId(),
                selected.getPriceList(),
                selected.getStartDate(),
                selected.getEndDate(),
                selected.getPrice(),
                validateCurrency(selected.getCurrency())
        );
    }

    private String validateCurrency(String currency) {
        if (currency == null || currency.length() != 3) {
            throw new IllegalStateException("Currency must be a 3-letter ISO code");
        }
        return currency;
    }
}
