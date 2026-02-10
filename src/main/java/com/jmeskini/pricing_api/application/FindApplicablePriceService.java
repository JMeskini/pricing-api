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
        List<Price> prices = priceRepository.findByProductAndBrand(query.productId(), query.brandId());
        log.debug("Candidate prices found count={} for productId={} brandId={}", prices.size(), query.productId(), query.brandId());
        Price selected = priceSelector.selectApplicable(prices, query.applicationDate())
                .orElseThrow(() -> new PriceNotFoundException(query.productId(), query.brandId()));

        return new PriceResponse(
                selected.getProductId(),
                selected.getBrandId(),
                selected.getPriceList(),
                selected.getStartDate(),
                selected.getEndDate(),
                selected.getPrice(),
                selected.getCurrency()
        );
    }
}
