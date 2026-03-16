package com.project.mylittleshop.service;

import com.project.mylittleshop.DTO.NewOrderRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
public class OrderClient {
    
    private final WebClient webClient;
    
    public OrderClient(WebClient webClient) {
        this.webClient = webClient;
    }
    
    public void createNewOrderForUser(Long userId, BigDecimal totalAmount, String orderItemId) {
        NewOrderRequestDTO dto = new NewOrderRequestDTO(userId, orderItemId, totalAmount);
        webClient.post()
                 .uri("api/v1/orders/create")
                 .bodyValue(dto)
                 .retrieve()
                 .bodyToMono(NewOrderRequestDTO.class)
                 .block();
    }
    
}
