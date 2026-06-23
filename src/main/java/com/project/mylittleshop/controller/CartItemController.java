package com.project.mylittleshop.controller;

import com.project.mylittleshop.DTO.CartItemDTO;
import com.project.mylittleshop.DTO.NewCartItemRequestDTO;
import com.project.mylittleshop.security.LoggedUser;
import com.project.mylittleshop.service.CartItemService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cartItem/")
public class CartItemController {
    private final CartItemService cartItemService;
    
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }
    
    @PostMapping("users/current")
    @PreAuthorize("isAuthenticated()")
    public String createNewCart(@AuthenticationPrincipal LoggedUser loggedUser) {
        return cartItemService.createNewCartForUser(loggedUser.getLoggedUserId());
    }
    
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CartItemDTO> getAllCarts() {
        return cartItemService.getAllCarts();
    }
    
    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public CartItemDTO getCartById(@PathVariable("id") String cartId) {
        return cartItemService.getCartById(cartId);
    }
    
    @GetMapping("users/current")
    @PreAuthorize("isAuthenticated()")
    public CartItemDTO getCartsByUserId(@AuthenticationPrincipal LoggedUser loggedUser) {
        return cartItemService.getCartsByUserId(loggedUser.getLoggedUserId());
    }
    
    @PostMapping("{cartId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public CartItemDTO addNewProduct(@PathVariable("cartId") String cartId,
                                     @RequestBody @Valid
                                     NewCartItemRequestDTO newCartItemRequestDTO) throws BadRequestException {
        return cartItemService.addNewProduct(cartId, newCartItemRequestDTO);
    }
    
    @PatchMapping("cartCheckout/{cartId}")
    @PreAuthorize("isAuthenticated()")
    public void checkoutCart(@PathVariable("cartId") String cartId) {
        cartItemService.checkoutCart(cartId);
    }
}
