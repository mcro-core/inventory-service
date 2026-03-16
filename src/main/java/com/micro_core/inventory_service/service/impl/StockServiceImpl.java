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
    public void stockUpdate(Long productId, Integer quantity) {
        Stock stock = stockRepo.findByProductId(productId)
                        .orElse(Stock.builder()
                        .productId(productId)
                        .quantity(0)
                        .build());
        stockRepo.save(stock);

        int newQuantity = stock.getQuantity() + quantity;

        if (newQuantity < 0) {
            throw new RuntimeException("Insufficient stock! Available: " + stock.getQuantity() + ", Requested: " + Math.abs(quantity));
        }

        stock.setQuantity(newQuantity);
        stockRepo.save(stock);
    }

    @Override
    public List<StockResponseDto> getStock(List<Long> productId) {
        return stockRepo.findByProductIdIn(productId).stream()
                .map(stock -> StockResponseDto.builder()
                        .productId(stock.getProductId())
                        .isInStock(stock.getQuantity() > 0)
                        .quantity(stock.getQuantity())
                        .build()
                ).toList();
    }
}
