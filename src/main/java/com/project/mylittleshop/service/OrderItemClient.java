package com.project.mylittleshop.service;

import com.project.mylittleshop.DTO.NewOrderItemRequestDTO;
import com.project.mylittleshop.DTO.OrderItemDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class OrderItemClient {
    private final WebClient webClient;
    
    public OrderItemClient(WebClient webClient) {
        this.webClient = webClient;
    }
    
    public String retrieveOrderItemId() {
        return webClient.post()
                        .uri("/api/v1/orderItem/create")
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
    }
    
    public List<OrderItemDTO> addNewProductsToOrder(String orderItemId, List<NewOrderItemRequestDTO> products) {
        return webClient.patch()
                        .uri("/api/v1/orderItem/{orderItemId}/addProduct", orderItemId)
                        .bodyValue(products)
                        .retrieve()
                        .bodyToFlux(OrderItemDTO.class)
                        .collectList()
                        .block();
    }
    
}
