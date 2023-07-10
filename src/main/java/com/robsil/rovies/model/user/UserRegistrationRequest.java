package com.robsil.rovies.model.user;

public record UserRegistrationRequest(
        String name,
        String email,
        String password
) {
}
