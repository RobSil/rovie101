package com.robsil.rovies.data.repository;

import com.robsil.rovies.data.domain.Movie;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieRepository extends R2dbcRepository<Movie, Long> {

    Mono<Movie> findByName(String name);

    @Query("select * from movies movie offset :page limit :pageSize")
    Flux<Movie> findAllPageable(Integer page, Integer pageSize);

//    @Query("select * from movies movie where movie.id in " +
//            "(select mr.movie_id from movie_rates mr where mr.user_id = :userId) " +
//            "offset :page limit :pageSize")
    @Query("select * from movies movie inner join movie_rates mr on movie.id = mr.movie_id " +
            "offset :page limit :pageSize")
    Flux<Movie> findAllRatedPageable(Integer page, Integer pageSize, Long userId);

}
