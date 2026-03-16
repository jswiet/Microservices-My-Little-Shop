package com.project.mylittleshop.controller;

import com.project.mylittleshop.DTO.CartItemDTO;
import com.project.mylittleshop.DTO.NewCartItemRequestDTO;
import com.project.mylittleshop.service.CartItemService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cartItem/")
public class CartItemController {
    private final CartItemService cartItemService;
    
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }
    
    @PostMapping("user/{userId}")
    public String createNewCart(@PathVariable("userId") Long userId) {
        return cartItemService.createNewCartForUser(userId);
    }
    
    @GetMapping
    public List<CartItemDTO> getAllCarts() {
        return cartItemService.getAllCarts();
    }
    
    @GetMapping("{id}")
    public CartItemDTO getCartById(@PathVariable("id") String cartId) {
        return cartItemService.getCartById(cartId);
    }
    
    @GetMapping("user/{id}")
    public CartItemDTO getCartsByUserId(@PathVariable("id") Long userId) {
        return cartItemService.getCartsByUserId(userId);
    }
    
    @PostMapping("{cartId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemDTO addNewProduct(@PathVariable("cartId") String cartId,
                                     @RequestBody @Valid
                                     NewCartItemRequestDTO newCartItemRequestDTO) throws BadRequestException {
        return cartItemService.addNewProduct(cartId, newCartItemRequestDTO);
    }
    
    @PatchMapping("cartCheckout/{cartId}")
    public void checkoutCart(@PathVariable("cartId") String cartId) {
        cartItemService.checkoutCart(cartId);
    }
}
