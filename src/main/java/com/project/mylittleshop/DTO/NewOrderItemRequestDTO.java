package com.project.mylittleshop.DTO;

import java.math.BigDecimal;

public record NewOrderItemRequestDTO(
        Long productId,
        Integer quantity,
        BigDecimal priceAtTime
) {
}
