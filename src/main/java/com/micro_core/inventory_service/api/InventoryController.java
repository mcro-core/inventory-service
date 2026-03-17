package com.micro_core.inventory_service.api;

import com.micro_core.inventory_service.dto.request.StockRequestDto;
import com.micro_core.inventory_service.dto.response.StockResponseDto;
import com.micro_core.inventory_service.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/inventory")
public class InventoryController {
    private final StockService stockService;

    @PostMapping("/update-stock")
    @ResponseStatus(HttpStatus.OK)
    public void updateStock(@RequestBody List<StockRequestDto> stockRequestDtoList){
        stockService.stockUpdate(stockRequestDtoList);
    }

    @PostMapping("/get-stock-list")
    public List<StockResponseDto> getStock(@RequestBody  List<Long> productId){
        return stockService.getStock(productId);
    }

    @PostMapping("/check-stock")
    public Boolean checkProductStock(@RequestBody  List<StockRequestDto> stockRequestDtoList){
        return stockService.checkStock(stockRequestDtoList);
    }


}
