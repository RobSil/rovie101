package com.robsil.rovies.service;

import com.robsil.rovies.data.domain.MovieRate;
import com.robsil.rovies.model.movieRate.MovieRateCreateRequest;
import com.robsil.rovies.model.movieRate.MovieRateDto;
import com.robsil.rovies.util.mapper.MovieRateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieRateFacadeService {

    private final MovieRateService movieRateService;
    private final UserService userService;
    private final MovieRateMapper movieRateMapper;

    public Mono<MovieRateDto> rateMovie(MovieRateCreateRequest request, Principal principal) {
        return userService.findByPrincipal(principal)
                .onErrorResume(e -> Mono.empty())
                .flatMap(user -> movieRateService.findByUserIdAndMovieId(user.getId(), request.movieId())
                        .flatMap(movieRate -> {
                            movieRate.setRate(request.rate());
                            return movieRateService.saveEntity(movieRate);
                        })
                        .switchIfEmpty(movieRateService.saveEntity(MovieRate.builder()
                                .rate(request.rate())
                                .userId(user.getId())
                                .movieId(request.movieId())
                                .build())))
                .map(mr -> movieRateMapper.toDto(mr));
    }
}
