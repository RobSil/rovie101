package com.robsil.rovies.data.repository;

import com.robsil.rovies.data.domain.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieRepository extends R2dbcRepository<Movie, Long> {

    Mono<Movie> findByName(String name);

    @Query("select * from movies movie offset :page limit :pageSize")
    Flux<Movie> findAllPageable(Integer page, Integer pageSize);

}
