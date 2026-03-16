package com.project.mylittleshop.entity;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Document
public class CartItem {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private Long userId;
    
    private List<ProductAndQuantityAndPrice> productAndQuantityAndPrice;
    
    private BigDecimal cartTotal;
    
    public CartItem() {
    }
    public CartItem(Long userId) {
        this.userId = userId;
    }
    
    public CartItem(Long userId, List<ProductAndQuantityAndPrice> productAndQuantityAndPrice, BigDecimal cartTotal) {
        this.userId = userId;
        this.productAndQuantityAndPrice = productAndQuantityAndPrice;
        this.cartTotal = cartTotal;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public List<ProductAndQuantityAndPrice> getProductAndQuantityAndPrice() {
        return productAndQuantityAndPrice;
    }
    public void setProductAndQuantityAndPrice(List<ProductAndQuantityAndPrice> productAndQuantityAndPrice) {
        this.productAndQuantityAndPrice = productAndQuantityAndPrice;
    }
    public BigDecimal getCartTotal() {
        return cartTotal;
    }
    public void setCartTotal(BigDecimal cartTotal) {
        this.cartTotal = cartTotal;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id) && Objects.equals(userId,
                cartItem.userId) && Objects.equals(productAndQuantityAndPrice,
                cartItem.productAndQuantityAndPrice) && Objects.equals(cartTotal, cartItem.cartTotal);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productAndQuantityAndPrice, cartTotal);
    }
    @Override
    public String toString() {
        return "CartItem{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", productAndQuantities=" + productAndQuantityAndPrice +
                ", totalAmount=" + cartTotal +
                '}';
    }
}
