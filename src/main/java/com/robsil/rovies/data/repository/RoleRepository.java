package com.robsil.rovies.data.repository;

import com.robsil.rovies.data.domain.Role;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface RoleRepository extends R2dbcRepository<Role, Long> {

    @Query("select * from roles where id in (select user_role.role_id from users_roles user_role where user_id = :userId)")
    Flux<Role> findAllByUserId(Long userId);

}
