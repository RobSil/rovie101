package com.robsil.rovies.controller;

import com.robsil.rovies.data.domain.User;
import com.robsil.rovies.data.repository.MovieRepository;
import com.robsil.rovies.model.movie.MovieDto;
import com.robsil.rovies.model.movie.RatedMovieDto;
import com.robsil.rovies.model.movierate.MovieRateDto;
import com.robsil.rovies.model.movierate.SimpleMovieRateCreateRequest;
import com.robsil.rovies.service.MovieFacadeService;
import com.robsil.rovies.service.MovieRateFacadeService;
import com.robsil.rovies.service.MovieRateService;
import com.robsil.rovies.service.MovieService;
import com.robsil.rovies.util.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieFacadeService movieFacadeService;
    private final MovieRateFacadeService movieRateFacadeService;
    private final MovieRateService movieRateService;
    private final MovieMapper movieMapper;
    private final MovieRepository movieRepository;

    @GetMapping
    public Flux<RatedMovieDto> getPageable(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return movieFacadeService.getPageableSorted(page, pageSize);
    }

    @GetMapping("/rated")
    public Flux<RatedMovieDto> getRatedPageable(@RequestParam Integer page,
                                                @RequestParam Integer pageSize,
                                                @AuthenticationPrincipal User user) {
        return movieFacadeService.getRatedPageable(page, pageSize, user);
    }

    @GetMapping("/{id}")
    public Mono<MovieDto> getById(@PathVariable Long id) {
        return movieFacadeService.findById(id)
                .switchIfEmpty(Mono.error(new HttpClientErrorException(HttpStatusCode.valueOf(404))));
    }

    @PostMapping("/{id}/rate")
    public Mono<MovieRateDto> createRate(@PathVariable Long id,
                                         @RequestBody SimpleMovieRateCreateRequest request,
                                         @AuthenticationPrincipal User user) {
        return movieRateFacadeService.rateMovie(id, request, user);
    }

    @DeleteMapping("/{id}/rate")
    public Mono<Void> deleteRateRate(@RequestParam Long id, @AuthenticationPrincipal User user) {
        return movieRateFacadeService.deleteRateByMovieAndUser(id, user);
    }
}
