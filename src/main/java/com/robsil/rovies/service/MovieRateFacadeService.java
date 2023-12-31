package com.robsil.rovies.service;

import com.robsil.rovies.data.domain.MovieRate;
import com.robsil.rovies.data.domain.User;
import com.robsil.rovies.model.movierate.MovieRateCreateRequest;
import com.robsil.rovies.model.movierate.MovieRateDto;
import com.robsil.rovies.model.movierate.SimpleMovieRateCreateRequest;
import com.robsil.rovies.util.mapper.MovieRateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieRateFacadeService {

    private final MovieRateService movieRateService;
    private final UserService userService;
    private final MovieRateMapper movieRateMapper;

    public Mono<MovieRateDto> rateMovie(MovieRateCreateRequest request, User user) {
        return movieRateService.findByUserIdAndMovieId(user.getId(), request.movieId())
                        .flatMap(movieRate -> {
                            movieRate.setRate(request.rate());
                            return movieRateService.saveEntity(movieRate);
                        })
                        .switchIfEmpty(movieRateService.saveEntity(MovieRate.builder()
                                .rate(request.rate())
                                .userId(user.getId())
                                .movieId(request.movieId())
                                .build()))
                .map(mr -> movieRateMapper.toDto(mr));
    }

    public Mono<MovieRateDto> rateMovie(Long id, SimpleMovieRateCreateRequest request, User user) {
        return rateMovie(new MovieRateCreateRequest(id, request), user);
    }

    public Mono<Void> deleteRateByMovieAndUser(Long movieId, User user) {
        return movieRateService.findByUserIdAndMovieId(user.getId(), movieId)
                .flatMap(mr -> movieRateService.deleteById(mr.getId()));
    }
}
