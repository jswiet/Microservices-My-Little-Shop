package com.project.mylittleshop.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record NewOrderRequestDTO(
 
        Long userId,
        
        String orderItemId,
        
        @DecimalMin(
                value = "0.01",
                message = "Total amount must be greater than 0.01")
        BigDecimal totalAmount
) {
}
