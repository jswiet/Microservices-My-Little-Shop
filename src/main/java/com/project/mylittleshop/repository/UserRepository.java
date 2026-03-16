package com.project.mylittleshop.repository;

import com.project.mylittleshop.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.address.country = ?2, u.address.city = ?3, u.address.street = ?4, u.address.postCode = ?5 WHERE u.id = ?1")
    int updateAddress(Long userId, String country, String city, String street, String postCode);
    
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.passwordHashed=?2 WHERE u.id=?1")
    int changePassword(Long userId, String newPassword);
}
