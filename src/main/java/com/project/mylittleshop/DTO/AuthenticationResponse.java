package com.project.mylittleshop.DTO;

import java.io.Serializable;

public record AuthenticationResponse(
        String jwt
) implements Serializable {
}
