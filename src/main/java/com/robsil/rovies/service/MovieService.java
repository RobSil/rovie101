package com.robsil.rovies.service;

import com.robsil.rovies.data.domain.Movie;
import com.robsil.rovies.data.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Mono<Movie> findByName(String name) {
        return movieRepository.findByName(name);
    }

    public Mono<Movie> findByid(Long id) {
        return movieRepository.findById(id);
    }

    public Mono<Movie> saveEntity(Movie movie) {
//        movie.setId(1L);
//        return Mono.just(movie);
//        return movieRepository.save(movie);
        var result = findByName(movie.getName())
                .hasElement()
                .flatMap(bool -> Boolean.FALSE.equals(bool) ? movieRepository.save(movie) : Mono.empty())
                .onErrorResume(e -> Mono.empty());
        result.subscribe(movieItem -> log.info("saveEntity movie: " + movieItem.toString()));
        return result;
    }
}
