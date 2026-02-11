package com.jmeskini.pricing_api.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import com.jmeskini.pricing_api.domain.Price;
import com.jmeskini.pricing_api.domain.PriceRepository;

@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepository {
    private final SpringDataPriceRepository repository;

    @Override
    public List<Price> findByProductAndBrandAndDate(long productId, long brandId, LocalDateTime applicationDate) {
        return repository
                .findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        productId,
                        brandId,
                        applicationDate,
                        applicationDate
                )
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Price toDomain(PriceEntity entity) {
        return new Price(
                entity.getBrandId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriceList(),
                entity.getProductId(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurrency()
        );
    }
}
