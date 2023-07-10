package com.robsil.rovies.controller;

import com.robsil.rovies.model.user.UserDto;
import com.robsil.rovies.model.user.UserRegistrationRequest;
import com.robsil.rovies.service.UserFacadeService;
import com.robsil.rovies.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserFacadeService userFacadeService;

    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserRegistrationRequest request) {
        return userFacadeService.register(request);
    }

}
