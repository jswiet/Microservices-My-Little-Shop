package com.project.mylittleshop.DTO.DTOMappers;

import com.project.mylittleshop.DTO.CartItemDTO;
import com.project.mylittleshop.entity.CartItem;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CartItemDTOMapper implements Function<CartItem, CartItemDTO> {
    
    @Override
    public CartItemDTO apply(CartItem cartItem) {
        return new CartItemDTO(
                cartItem.getId(),
                cartItem.getUserId(),
                cartItem.getProductAndQuantityAndPrice(),
                cartItem.getCartTotal()
        );
    }
}
