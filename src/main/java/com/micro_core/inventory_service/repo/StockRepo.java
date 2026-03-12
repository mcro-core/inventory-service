package com.micro_core.inventory_service.repo;

import com.micro_core.inventory_service.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepo extends JpaRepository<Stock, Long> {
    Optional<Stock> findBySku(String sku);

    List<Stock> findBySkuIn(List<String> sku);
}
