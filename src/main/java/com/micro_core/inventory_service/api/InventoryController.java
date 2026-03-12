package com.micro_core.inventory_service.api;

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
    public void updateStock(@RequestParam String skuCode, @RequestParam Integer quantity){
        stockService.stockUpdate(skuCode, quantity);
    }

    @GetMapping
    public List<StockResponseDto> getStock(@RequestParam  List<String> skuCode){
        return stockService.getStock(skuCode);
    }
}
