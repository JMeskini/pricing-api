package com.jmeskini.pricing_api.infrastructure.web;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import com.jmeskini.pricing_api.application.FindApplicablePriceUseCase;
import com.jmeskini.pricing_api.application.GetPriceQuery;
import com.jmeskini.pricing_api.application.PriceResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceController {
    private final FindApplicablePriceUseCase useCase;

    @GetMapping
    public ResponseEntity<PriceResponse> getPrice(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam("productId") long productId,
            @RequestParam("brandId") long brandId
    ) {
        PriceResponse response = useCase.execute(new GetPriceQuery(date, productId, brandId));
        return ResponseEntity.ok(response);
    }
}
