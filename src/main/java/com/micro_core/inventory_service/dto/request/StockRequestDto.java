package com.micro_core.inventory_service.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockRequestDto {
    private String sku;
    private Integer quantity;
}
