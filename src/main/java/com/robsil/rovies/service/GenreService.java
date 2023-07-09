package com.robsil.rovies.service;

import com.robsil.rovies.data.domain.Genre;
import com.robsil.rovies.data.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.concurrent.Flow;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public Mono<Genre> saveEntity(Genre genre) {
        return genreRepository.save(genre);
    }

    public Flux<Genre> findAllByMovieId(Long movieId) {
        return genreRepository.findAllByMovieId(movieId);
    }

    public Flux<Genre> findAllGenresByTmdbIdIn(Collection<Long> tmdbIds) {
        return genreRepository.findAllByTmdbIdIn(tmdbIds);
    }
}
