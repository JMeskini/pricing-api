package com.jmeskini.pricing_api.application;

import java.time.LocalDateTime;

public record GetPriceQuery(LocalDateTime applicationDate, long productId, long brandId) {
}
