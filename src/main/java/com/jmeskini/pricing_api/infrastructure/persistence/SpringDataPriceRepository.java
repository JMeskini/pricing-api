package com.jmeskini.pricing_api.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {
    List<PriceEntity> findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            long productId,
            long brandId,
            LocalDateTime applicationDate,
            LocalDateTime applicationDateEnd
    );
}
