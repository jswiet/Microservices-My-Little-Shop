package com.project.mylittleshop.DTO;

import com.project.mylittleshop.entity.Address;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        Address address,
        Boolean enabled,
        Boolean locked
) {
}
