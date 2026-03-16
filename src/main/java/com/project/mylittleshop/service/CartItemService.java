package com.project.mylittleshop.service;

import com.project.mylittleshop.DTO.CartItemDTO;
import com.project.mylittleshop.DTO.DTOMappers.CartItemDTOMapper;
import com.project.mylittleshop.DTO.NewCartItemRequestDTO;
import com.project.mylittleshop.DTO.NewOrderItemRequestDTO;
import com.project.mylittleshop.entity.CartItem;
import com.project.mylittleshop.entity.ProductAndQuantityAndPrice;
import com.project.mylittleshop.exception.ResourceNotFound;
import com.project.mylittleshop.repository.CartItemRepository;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemDTOMapper cartItemDTOMapper;
    private final ProductClient productClient;
    private final OrderItemClient orderItemClient;
    private final OrderClient orderClient;
    private final InventoryClient inventoryClient;
    
    public CartItemService(CartItemRepository cartItemRepository, CartItemDTOMapper cartItemDTOMapper, ProductClient productClient, OrderItemClient orderItemClient, OrderClient orderClient, InventoryClient inventoryClient) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemDTOMapper = cartItemDTOMapper;
        this.productClient = productClient;
        this.orderItemClient = orderItemClient;
        this.orderClient = orderClient;
        this.inventoryClient = inventoryClient;
    }
    public List<CartItemDTO> getAllCarts() {
        return cartItemRepository.findAll()
                                 .stream()
                                 .filter(c -> c.getUserId() != null)
                                 .map(cartItemDTOMapper)
                                 .collect(Collectors.toList());
    }
    
    public CartItemDTO getCartById(String cartId) {
        return cartItemRepository.findById(cartId)
                                 .map(cartItemDTOMapper)
                                 .orElseThrow(() -> new ResourceNotFound("Cart with id: " + cartId + " not found"));
    }
    public CartItemDTO getCartsByUserId(Long userId) {
        return cartItemRepository.findCartItemByUserId(userId)
                                 .map(cartItemDTOMapper)
                                 .orElseThrow(() -> new ResourceNotFound("USer with id: " + userId + " not found"));
    }
    
    public String createNewCartForUser(Long userId) {
        CartItem cartItem = new CartItem(userId);
        cartItem.setUserId(userId);
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return savedCartItem.getId();
    }
    public BigDecimal calculatePrice(Long productId, int quantity) {
        return showPriceAtTime(productId).multiply(new BigDecimal(quantity));
    }
    
    public BigDecimal showPriceAtTime(Long productId) {
        return productClient.getProductPriceByProductId(productId);
    }
    
    public CartItemDTO addNewProduct(String cartId,
                                     @Valid NewCartItemRequestDTO newCartItemRequestDTO) throws BadRequestException {
        CartItem cartItem = cartItemRepository.findById(cartId)
                                              .orElseThrow(() -> new ResourceNotFound(
                                                      "Cart with id: " + cartId + " not found"));
        Long productId = newCartItemRequestDTO.productId();
        int quantity = newCartItemRequestDTO.quantity();
        int stockQty = inventoryClient.getStockByProductId(productId);
        if (quantity > stockQty) {
            throw new BadRequestException("Not enough stock. Available only: " + stockQty);
        }
        BigDecimal productPriceTotal = calculatePrice(productId, quantity);
        
        if (cartItem.getProductAndQuantityAndPrice() == null) {
            cartItem.setProductAndQuantityAndPrice(new ArrayList<>());
        }
        cartItem.getProductAndQuantityAndPrice()
                .add(new ProductAndQuantityAndPrice(productId, quantity, productPriceTotal));
        
        BigDecimal cartTotal = BigDecimal.ZERO;
        for (ProductAndQuantityAndPrice i : cartItem.getProductAndQuantityAndPrice()) {
            cartTotal = cartTotal.add(i.getTotalPrice());
        }
        cartItem.setCartTotal(cartTotal);
        
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return cartItemDTOMapper.apply(savedCartItem);
    }
    
    public void checkoutCart(String cartId) {
        
        String orderItemId = orderItemClient.retrieveOrderItemId();
        if (orderItemId == null) {
            throw new IllegalStateException("OrderItem ID cannot be null");
        }
        CartItem cartItem = cartItemRepository.findById(cartId)
                                              .orElseThrow(
                                                      () -> new ResourceNotFound("Cart with id: " + cartId + " not " +
                                                              "found"));
        
        List<NewOrderItemRequestDTO> productsSendToOrder = cartItem.getProductAndQuantityAndPrice()
                                                                   .stream()
                                                                   .map(p -> new NewOrderItemRequestDTO(
                                                                           p.getProductId(),
                                                                           p.getQuantity(),
                                                                           p.getTotalPrice()))
                                                                   .toList();
        orderItemClient.addNewProductsToOrder(orderItemId, productsSendToOrder);
        orderClient.createNewOrderForUser(cartItem.getUserId(), cartItem.getCartTotal(), orderItemId);
        
    }
}
