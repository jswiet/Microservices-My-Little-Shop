package com.project.mylittleshop.DTO;

public record AuthenticationRequest(
        String email, String password
) {
}
