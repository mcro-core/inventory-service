package com.micro_core.inventory_service.service;

import com.micro_core.inventory_service.dto.request.StockRequestDto;
import com.micro_core.inventory_service.dto.response.StockResponseDto;

import java.util.List;

public interface StockService {
    public void stockUpdate(List<StockRequestDto> stockRequestDto);

    public List<StockResponseDto> getStock(List<Long> productId);

    public boolean checkStock(List<StockRequestDto> productIds);
}
