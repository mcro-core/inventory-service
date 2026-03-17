package com.micro_core.inventory_service.service.impl;

import com.micro_core.inventory_service.client.ProductClient;
import com.micro_core.inventory_service.dto.request.StockRequestDto;
import com.micro_core.inventory_service.dto.response.ResponseProductDto;
import com.micro_core.inventory_service.dto.response.StockResponseDto;
import com.micro_core.inventory_service.entity.Stock;
import com.micro_core.inventory_service.exceptions.ResourceNotFoundException;
import com.micro_core.inventory_service.exceptions.StockValidationException;
import com.micro_core.inventory_service.repo.StockRepo;
import com.micro_core.inventory_service.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class StockServiceImpl implements StockService {

    private final StockRepo stockRepo;
    private final ProductClient productClient;

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

    @Override
    public boolean checkStock(List<StockRequestDto> stockRequestDtoList) {

        for(StockRequestDto product : stockRequestDtoList){
                Optional<Stock> stockOptional = stockRepo.findByProductId(product.getProductId());
                if(stockOptional.isPresent()){

                    Stock stock = stockOptional.get();
                    if(stock.getQuantity() < product.getQuantity()){
                        ResponseProductDto productDetails = this.getProductShortDetails(product.getProductId());
                        throw new StockValidationException(String.format("Product %s (%s) is currently out of stock. Only %d units available.",
                                productDetails.getProductName(), productDetails.getSkuCode(), stock.getQuantity()));
                    }
                }else{
                    throw new ResourceNotFoundException(String.format("%d this product id not found !", product.getProductId()));
                }

        }
        return true;
    }

    public ResponseProductDto getProductShortDetails(Long productId){
            return productClient.findProductShortDetails(productId);
    }

}
