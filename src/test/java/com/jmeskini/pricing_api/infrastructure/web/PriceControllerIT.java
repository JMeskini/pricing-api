package com.jmeskini.pricing_api.infrastructure.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private String format(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Test
    @DisplayName("Test 1 - 2020-06-14 10:00 for product 35455 and brand 1")
    void test1_requestAt2020_06_14_10_00() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("date", format(LocalDateTime.of(2020, 6, 14, 10, 0, 0)))
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    @DisplayName("Test 2 - 2020-06-14 16:00 for product 35455 and brand 1")
    void test2_requestAt2020_06_14_16_00() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("date", format(LocalDateTime.of(2020, 6, 14, 16, 0, 0)))
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    @DisplayName("Test 3 - 2020-06-14 21:00 for product 35455 and brand 1")
    void test3_requestAt2020_06_14_21_00() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("date", format(LocalDateTime.of(2020, 6, 14, 21, 0, 0)))
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    @DisplayName("Test 4 - 2020-06-15 10:00 for product 35455 and brand 1")
    void test4_requestAt2020_06_15_10_00() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("date", format(LocalDateTime.of(2020, 6, 15, 10, 0, 0)))
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.50));
    }

    @Test
    @DisplayName("Test 5 - 2020-06-16 21:00 for product 35455 and brand 1")
    void test5_requestAt2020_06_16_21_00() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("date", format(LocalDateTime.of(2020, 6, 16, 21, 0, 0)))
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95));
    }
}
