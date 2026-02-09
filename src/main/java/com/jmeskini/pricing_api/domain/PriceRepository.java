package com.jmeskini.pricing_api.domain;

import java.util.List;

public interface PriceRepository {
    List<Price> findByProductAndBrand(long productId, long brandId);
}
