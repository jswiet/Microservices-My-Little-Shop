package com.project.mylittleshop.entity;

import java.math.BigDecimal;

public class ProductAndQuantityAndPrice {
    private Long productId;
    private Integer quantity;
    private BigDecimal totalPrice;
    
    public ProductAndQuantityAndPrice(Long productId, int quantity, BigDecimal totalPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
