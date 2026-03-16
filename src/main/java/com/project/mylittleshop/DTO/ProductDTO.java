package com.project.mylittleshop.DTO;

import java.math.BigDecimal;

public record ProductDTO(
        Long productId,
        BigDecimal priceAtTime
) {

}
