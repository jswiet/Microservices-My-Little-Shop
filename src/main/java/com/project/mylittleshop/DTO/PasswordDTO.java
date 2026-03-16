package com.project.mylittleshop.DTO;

import jakarta.validation.constraints.NotNull;

public record PasswordDTO(
        @NotNull
        String oldPassword,
        
        @NotNull
        String newPassword
) {
}
