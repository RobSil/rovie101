package com.robsil.rovies.controller;

import com.robsil.rovies.data.domain.Movie;
import com.robsil.rovies.service.MovieService;
import com.robsil.rovies.service.TMDBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MovieController {

    private final TMDBService tmdbService;
    private final MovieService movieService;

    @GetMapping("/parseMovies")
    List<Movie> parseMovies(@RequestParam int page) {
        return tmdbService.getTopRatedMovies(page);
    }

    @GetMapping("/reactorDemo")
    Mono<?> reactorDemo() {
        Mono.empty().subscribe(it -> log.info("111111111111111111111111"));
        return Mono.empty();
    }

    @GetMapping("/movies/{id}")
    Mono<Movie> findById(@PathVariable Long id) {
        var result = movieService.findByid(id);
        result.subscribe(movie -> log.info("findById movie: " + movie.toString()));
        result.hasElement().subscribe(bool -> log.info(bool.toString()));
        return result;
    }

}
