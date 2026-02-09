package com.jmeskini.pricing_api.infrastructure.config;

import com.jmeskini.pricing_api.application.FindApplicablePriceService;
import com.jmeskini.pricing_api.application.FindApplicablePriceUseCase;
import com.jmeskini.pricing_api.domain.PriceRepository;
import com.jmeskini.pricing_api.domain.PriceSelector;
import com.jmeskini.pricing_api.infrastructure.persistence.PriceRepositoryAdapter;
import com.jmeskini.pricing_api.infrastructure.persistence.SpringDataPriceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PricingConfig {

    @Bean
    public PriceSelector priceSelector() {
        return new PriceSelector();
    }

    @Bean
    public PriceRepository priceRepository(SpringDataPriceRepository repository) {
        return new PriceRepositoryAdapter(repository);
    }

    @Bean
    public FindApplicablePriceUseCase getApplicablePriceUseCase(
            PriceRepository priceRepository,
            PriceSelector priceSelector
    ) {
        return new FindApplicablePriceService(priceRepository, priceSelector);
    }
}
