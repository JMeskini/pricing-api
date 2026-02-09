package com.jmeskini.pricing_api.infrastructure.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {
    List<PriceEntity> findByProductIdAndBrandId(long productId, long brandId);
}
