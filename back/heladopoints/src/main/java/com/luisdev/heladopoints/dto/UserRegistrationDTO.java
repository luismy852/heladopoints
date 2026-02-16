package com.luisdev.heladopoints.dto;

public record UserRegistrationDTO(
        String username,
        String email,
        String password
) {
}
