package com.robsil.rovies.controller;

import com.robsil.rovies.data.domain.User;
import com.robsil.rovies.model.movierate.MovieRateCreateRequest;
import com.robsil.rovies.model.movierate.MovieRateDto;
import com.robsil.rovies.service.MovieRateFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movieRates")
public class MovieRateController {

    private final MovieRateFacadeService movieRateFacadeService;


    @PostMapping
    public Mono<MovieRateDto> rateMovie(@RequestBody MovieRateCreateRequest request, @AuthenticationPrincipal User user) {
        return movieRateFacadeService.rateMovie(request, user);
    }

}
