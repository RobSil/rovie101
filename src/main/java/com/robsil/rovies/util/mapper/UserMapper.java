package com.robsil.rovies.util.mapper;

import com.robsil.rovies.data.domain.Role;
import com.robsil.rovies.data.domain.User;
import com.robsil.rovies.model.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "roles", target = "roles")
    UserDto toDto(User user, List<Role> roles);
}
