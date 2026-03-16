package com.micro_core.inventory_service.dto.response;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockResponseDto {
    private Long productId;
    private Integer quantity;
    private boolean isInStock;
}
