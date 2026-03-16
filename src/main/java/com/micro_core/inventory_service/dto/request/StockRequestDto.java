package com.micro_core.inventory_service.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockRequestDto {
    private Long productId;
    private Integer quantity;
}
