package com.project.mylittleshop.DTO;

import java.io.Serializable;

public record MailDTO(
        String email,
        String firstName,
        String link
) implements Serializable {
}
