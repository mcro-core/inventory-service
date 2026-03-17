package com.micro_core.inventory_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProductDto {
    private String productName;
    private String skuCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
