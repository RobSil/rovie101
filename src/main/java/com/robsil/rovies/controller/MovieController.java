package com.robsil.rovies.controller;

import com.robsil.rovies.data.domain.User;
import com.robsil.rovies.model.movie.MovieDto;
import com.robsil.rovies.model.movie.RatedMovieDto;
import com.robsil.rovies.service.MovieFacadeService;
import com.robsil.rovies.service.MovieService;
import com.robsil.rovies.util.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieFacadeService movieFacadeService;
    private final MovieMapper movieMapper;

    @GetMapping
    public Flux<MovieDto> getPageable(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return movieFacadeService.getPageable(page, pageSize);
    }

    @GetMapping("/rated")
    public Flux<RatedMovieDto> getRatedPageable(@RequestParam Integer page,
                                                @RequestParam Integer pageSize,
                                                @AuthenticationPrincipal User user) {
        return movieFacadeService.getRatedPageable(page, pageSize, user);
    }

    @GetMapping("/{id}")
    public Mono<MovieDto> getById(@PathVariable Long id) {
        return movieFacadeService.findById(id);
    }


}
