package com.project.mylittleshop.repository;

import com.project.mylittleshop.entity.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartItemRepository extends MongoRepository<CartItem, String> {
    Optional<CartItem> findCartItemByUserId(Long userId);
}
