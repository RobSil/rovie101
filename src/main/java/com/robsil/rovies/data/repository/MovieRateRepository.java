package com.robsil.rovies.data.repository;

import com.robsil.rovies.data.domain.MovieRate;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieRateRepository extends R2dbcRepository<MovieRate, Long> {

    Mono<MovieRate> findByUserIdAndMovieId(Long userId, Long movieId);

    @Query("select * from movie_rates mr where mr.movie_id = :movieId offset :page limit :pageSize")
    Flux<MovieRate> findAllByMovieId(Long movieId, int page, int pageSize);

    @Query("select * from movie_rates mr where mr.user_id = :userId offset :page limit :pageSize")
    Flux<MovieRate> findAllByUserId(Long userId, int page, int pageSize);

}
