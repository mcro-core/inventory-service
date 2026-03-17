package com.micro_core.inventory_service.exceptions;

public class StockValidationException extends RuntimeException {
    public StockValidationException(String message) {
        super(message);
    }
}
