package com.robsil.rovies.service;

import com.robsil.rovies.data.domain.Movie;
import com.robsil.rovies.data.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final R2dbcEntityTemplate entityTemplate;

    public Mono<Movie> findByName(String name) {
        return movieRepository.findByName(name);
    }

    public Mono<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    public Mono<Movie> saveEntity(Movie movie) {
        return movieRepository.save(movie);
    }

    public Flux<Movie> getPageable(Integer page, Integer pageSize) {
        return movieRepository.findAllPageable(page, pageSize);
    }
}
