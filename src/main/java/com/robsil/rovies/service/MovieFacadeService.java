package com.robsil.rovies.service;

import com.robsil.rovies.data.domain.User;
import com.robsil.rovies.model.movie.MovieDto;
import com.robsil.rovies.model.movie.RatedMovieDto;
import com.robsil.rovies.util.mapper.GenreMapper;
import com.robsil.rovies.util.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieFacadeService {

    private final MovieService movieService;
    private final GenreService genreService;
    private final MovieGenreService movieGenreService;
    private final MovieRateService movieRateService;
    private final MovieMapper movieMapper;
    private final GenreMapper genreMapper;

    public Flux<MovieDto> getPageable(int page, int pageSize) {
        return movieService.getPageable(page, pageSize)
                .flatMap(movie -> genreService.findAllByMovieId(movie.getId())
                        .collectList()
                        .map(genres -> movieMapper.toDto(movie, genres.parallelStream().map(genreMapper::toDto).toList())));
    }

    public Flux<RatedMovieDto> getRatedPageable(int page, int pageSize, User user) {
        return movieRateService.findAllByUserId(user.getId(), page, pageSize)
                .flatMap(mr -> movieService.findById(mr.getMovieId())
                        .flatMap(movie -> genreService.findAllByMovieId(movie.getId())
                                .collectList()
                                .map(genres -> movieMapper.toRatedDto(
                                        movie,
                                        genres.parallelStream().map(genreMapper::toDto).toList(),
                                        mr.getRate())
                                )
                        )
                );
//        return movieService.getRatedPageable(page, pageSize, user.getId())
//                .flatMap(movie -> genreService.findAllByMovieId(movie.getId())
//                        .collectList()
//                        .map(genres -> movieMapper.toRatedDto(movie, genres.parallelStream().map(genreMapper::toDto).toList())));
    }

    public Mono<MovieDto> findById(Long id) {
        return movieService.findById(id)
                .onErrorResume(e -> Mono.empty())
                .flatMap(movie -> genreService.findAllByMovieId(movie.getId())
                        .collectList()
                        .map(genres -> movieMapper.toDto(movie, genres.parallelStream().map(genreMapper::toDto).toList())));
    }
}
