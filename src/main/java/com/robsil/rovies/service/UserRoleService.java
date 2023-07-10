package com.robsil.rovies.service;

import com.robsil.rovies.data.domain.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRoleService {

    private final R2dbcEntityTemplate entityTemplate;

    public Flux<UserRole> findByUserId(Long userId) {
        return entityTemplate
                .select(
                        Query.query(
                                Criteria
                                        .where("userId")
                                        .is(userId)
                        ), UserRole.class
                );
    }

}
