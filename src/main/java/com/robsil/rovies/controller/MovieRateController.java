package com.robsil.rovies.controller;

import com.robsil.rovies.model.movieRate.MovieRateCreateRequest;
import com.robsil.rovies.model.movieRate.MovieRateDto;
import com.robsil.rovies.service.MovieRateFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movieRates")
public class MovieRateController {

    private final MovieRateFacadeService movieRateFacadeService;


    @PostMapping
    public Mono<MovieRateDto> rateMovie(@RequestBody MovieRateCreateRequest request, Principal principal) {
        return movieRateFacadeService.rateMovie(request, principal);
    }

}
