package com.robsil.rovies.model.user;

import java.util.List;

public record UserDto(
        Long id,
        String name,
        String email,
        List<RoleDto> roles
) {
}
