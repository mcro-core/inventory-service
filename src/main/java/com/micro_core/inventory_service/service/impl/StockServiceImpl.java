package com.micro_core.inventory_service.service.impl;

import com.micro_core.inventory_service.dto.response.StockResponseDto;
import com.micro_core.inventory_service.entity.Stock;
import com.micro_core.inventory_service.repo.StockRepo;
import com.micro_core.inventory_service.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor

public class StockServiceImpl implements StockService {

    private final StockRepo stockRepo;

    @Override
    @Transactional
    public void stockUpdate(String skuCode, Integer quantity) {
        Stock stock = stockRepo.findBySku(skuCode)
                        .orElse(Stock.builder()
                        .sku(skuCode)
                        .quantity(0)
                        .build());
        stock.setQuantity(stock.getQuantity() + quantity);
        stockRepo.save(stock);
    }

    @Override
    public List<StockResponseDto> getStock(List<String> skuCodes) {

        return stockRepo.findBySkuIn(skuCodes).stream()
                .map(stock -> StockResponseDto.builder()
                        .sku(stock.getSku())
                        .isInStock(stock.getQuantity() > 0)
                        .quantity(stock.getQuantity())
                        .build()
                ).toList();
    }
}
