package com.robsil.rovies.data.repository;

import com.robsil.rovies.data.domain.Genre;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.concurrent.Flow;

public interface GenreRepository extends R2dbcRepository<Genre, Long> {

    Flux<Genre> findAllByTmdbIdIn(Collection<Long> tmdbIds);

    @Query("select * from genres genre where id in (select movieGenre.genre_id from movie_genre movieGenre where movie_id = :movieId)")
    Flux<Genre> findAllByMovieId(Long movieId);
}
