package com.micro_core.inventory_service.client;

import com.micro_core.inventory_service.dto.response.ResponseProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {
    @GetMapping("/api/v1/products/product-inventory/{productId}")
    ResponseProductDto findProductShortDetails(@PathVariable("productId") Long productId);
}
