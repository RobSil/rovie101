package com.robsil.rovies.service;

import com.robsil.rovies.data.domain.Role;
import com.robsil.rovies.data.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Flux<Role> findAllByUserId(Long userId) {
        return roleRepository.findAllByUserId(userId);
    }

}
