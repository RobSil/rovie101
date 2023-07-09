package com.robsil.rovies.data.repository;

import com.robsil.rovies.data.domain.Movie;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface MovieRepository extends R2dbcRepository<Movie, Long> {

    Mono<Movie> findByName(String name);

}
