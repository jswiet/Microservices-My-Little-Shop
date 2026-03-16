package com.project.mylittleshop.DTO;

import com.project.mylittleshop.entity.ProductAndQuantityAndPrice;

import java.math.BigDecimal;
import java.util.List;

public record CartItemDTO(
        
        String id,
        
        Long userId,
        
        List<ProductAndQuantityAndPrice> productAndQuantities,
        
        BigDecimal totalAmount) {
}
