package com.micro_core.inventory_service.service.impl;

import com.micro_core.inventory_service.dto.request.StockRequestDto;
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
    public void stockUpdate(List<StockRequestDto> stockRequestDto) {

        for(StockRequestDto  item : stockRequestDto){
            Stock stock = stockRepo.findByProductId(item.getProductId())
                    .orElse(Stock.builder()
                            .productId(item.getProductId())
                            .quantity(0)
                            .build());

            int newQuantity = stock.getQuantity() + item.getQuantity();

            if (newQuantity < 0) {
                throw new RuntimeException("Insufficient stock! Available: " + stock.getQuantity() + ", Requested: " + Math.abs(item.getQuantity()));
            }

            stock.setQuantity(newQuantity);
            stockRepo.save(stock);
        }

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
