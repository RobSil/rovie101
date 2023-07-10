package com.robsil.rovies.config;

import com.robsil.rovies.service.RoleService;
import com.robsil.rovies.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        return username -> userService
                .findByEmail(username)
                .switchIfEmpty(Mono.empty())
                .flatMap(user -> roleService.findAllByUserId(user.getId())
                        .collectList()
                        .map(roles -> {
                            user.setRoles(roles);
                            return user;
                        }));
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        http
                .authorizeExchange(customizer -> customizer.anyExchange().permitAll());

        http.formLogin(customizer -> {

        });

        return http.build();
    }

}
