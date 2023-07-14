package com.robsil.rovies.service;

import com.robsil.rovies.data.domain.MovieRate;
import com.robsil.rovies.data.repository.MovieRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieRateService {

    private final MovieRateRepository movieRateRepository;

    public Mono<MovieRate> saveEntity(MovieRate movieRate) {
        return movieRateRepository.save(movieRate);
    }

    public Mono<MovieRate> findByUserIdAndMovieId(Long userId, Long movieId) {
        return movieRateRepository.findByUserIdAndMovieId(userId, movieId);
    }

    public Flux<MovieRate> findAllByUserId(Long userId, int page, int pageSize) {
        return movieRateRepository.findAllByUserId(userId, page, pageSize);
    }

}
