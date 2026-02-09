package com.jmeskini.pricing_api.application;

public interface FindApplicablePriceUseCase {
    PriceResponse execute(GetPriceQuery query);
}
