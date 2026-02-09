package com.jmeskini.pricing_api.domain;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(long productId, long brandId) {
        super("No price found for product " + productId + " and brand " + brandId + " at given date");
    }
}
