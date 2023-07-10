package com.robsil.rovies.service;

import com.robsil.rovies.data.domain.User;
import com.robsil.rovies.model.user.UserDto;
import com.robsil.rovies.model.user.UserRegistrationRequest;
import com.robsil.rovies.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserFacadeService {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public Mono<UserDto> register(UserRegistrationRequest request) {
        return userService.saveEntity(User.builder()
                .name(request.name())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .build())
                .onErrorResume(e -> Mono.empty())
                .map(user -> userMapper.toDto(user, List.of()));
    }

}
