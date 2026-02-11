package com.jmeskini.pricing_api.infrastructure.web;

import java.time.LocalDateTime;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.jmeskini.pricing_api.application.FindApplicablePriceUseCase;
import com.jmeskini.pricing_api.application.GetPriceQuery;
import com.jmeskini.pricing_api.application.PriceResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PriceController {
    private final FindApplicablePriceUseCase useCase;

    @GetMapping
    public ResponseEntity<PriceResponse> getPrice(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam("productId") @Min(1) @Max(9_999_999_999L) long productId,
            @RequestParam("brandId") @Min(1) @Max(9_999_999_999L) long brandId
    ) {
        log.info("Price query received date={} productId={} brandId={}", date, productId, brandId);
        PriceResponse response = useCase.execute(new GetPriceQuery(date, productId, brandId));
        log.info("Price found priceList={} price={} currency={}", response.priceList(), response.price(), response.currency());
        return ResponseEntity.ok(response);
    }
}
