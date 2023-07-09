package com.robsil.rovies.service;

import com.robsil.rovies.data.domain.MovieGenre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieGenreService {

    private final R2dbcEntityTemplate r2dbcTemplate;

    public Flux<MovieGenre> findByMovieId(Long movieId) {
        return r2dbcTemplate.select(Query.query(Criteria.where("movie_id").is(movieId)), MovieGenre.class);
    }

    public Mono<MovieGenre> save(MovieGenre movieGenre) {
        Assert.notNull(movieGenre, "must not be null");

        return r2dbcTemplate.insert(MovieGenre.class)
                .into("movie_genre")
                .using(movieGenre);
    }

}
