package com.fintech.customerservice.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Feign client for product-service communication.
 * TODO: Implement methods to fetch product data from product-service.
 */
@FeignClient(name = "product-service")
public interface ProductServiceClient {

    // TODO: Add Feign methods like:
    // @GetMapping("/api/products/{id}")
    // ProductDTO getProductById(@PathVariable Long id);

}
