package com.robsil.rovies.service;

import com.robsil.rovies.data.domain.User;
import com.robsil.rovies.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono<User> saveEntity(User user) {
        return userRepository.save(user);
    }

    public Mono<User> findByPrincipal(Principal principal) {
        return findByEmail(principal.getName());
    }
}
