package com.project.mylittleshop.DTO;

public record OrderItemDTO(
        String orderItemId,
        Long productId,
        Integer quantity
) {
}