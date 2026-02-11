package com.jmeskini.pricing_api.domain;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository {
    List<Price> findByProductAndBrandAndDate(long productId, long brandId, LocalDateTime applicationDate);
}
