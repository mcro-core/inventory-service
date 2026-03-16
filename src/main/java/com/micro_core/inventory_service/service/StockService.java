package com.micro_core.inventory_service.service;

import com.micro_core.inventory_service.dto.response.StockResponseDto;

import java.util.List;

public interface StockService {
    public void stockUpdate(Long productId, Integer quantity);

    public List<StockResponseDto> getStock(List<Long> productId);
}
