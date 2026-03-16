package com.project.mylittleshop.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record NewCartItemRequestDTO(
        
        @NotNull
        Long productId,
        
        @Min(1)
        Integer quantity
) {

}
