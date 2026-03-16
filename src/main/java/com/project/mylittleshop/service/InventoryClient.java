package com.project.mylittleshop.service;

import com.project.mylittleshop.exception.ResourceNotFound;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class InventoryClient {
    
    private final WebClient webClient;
    
    public InventoryClient(WebClient webClient) {
        this.webClient = webClient;
    }
    
    public int getStockByProductId(Long productId) {
        Integer stockQty = webClient.get()
                                    .uri("/api/v1/inventory/product/{productId}", productId)
                                    .retrieve()
                                    .bodyToMono(Integer.class)
                                    .block();
        
        if (stockQty == null) {
            throw new ResourceNotFound("Inventory for productId: " + productId + " not found");
        }
        return stockQty;
    }
}
