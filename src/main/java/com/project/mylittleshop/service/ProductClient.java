package com.project.mylittleshop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
public class ProductClient {
    
    private final WebClient webClient;
    
    public ProductClient(WebClient webClient) {
        this.webClient = webClient;
    }
    
    public BigDecimal getProductPriceByProductId(Long productId) {
        return webClient.get()
                        .uri("/api/v1/products/{id}/price", productId)
                        .retrieve()
                        .bodyToMono(BigDecimal.class)
                        .block();
    }
}
