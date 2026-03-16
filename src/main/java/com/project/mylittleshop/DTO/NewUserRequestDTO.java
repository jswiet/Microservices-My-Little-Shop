package com.project.mylittleshop.DTO;

import com.project.mylittleshop.entity.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record NewUserRequestDTO(
        
        @NotBlank
        String firstName,
        
        @NotBlank
        String lastName,
        
        @NotBlank
        @Email
        String email,
        
        Address address,
        
        @NotBlank
        String password
        
        
) {
}
