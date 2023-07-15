package com.robsil.rovies.service;

import com.robsil.rovies.data.domain.Movie;
import com.robsil.rovies.data.domain.User;
import com.robsil.rovies.model.movie.MovieDto;
import com.robsil.rovies.model.movie.RatedMovieDto;
import com.robsil.rovies.util.mapper.GenreMapper;
import com.robsil.rovies.util.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

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
    private final R2dbcEntityTemplate entityTemplate;

    public Flux<MovieDto> getPageable(int page, int pageSize) {
        return movieService.getPageable(page, pageSize)
                .flatMap(movie -> genreService.findAllByMovieId(movie.getId())
                        .collectList()
                        .map(genres -> movieMapper.toDto(movie, genres.parallelStream().map(genreMapper::toDto).toList())));
    }

    public Flux<RatedMovieDto> getPageableSorted(int page, int pageSize) {
        return entityTemplate.getDatabaseClient().sql("""
                        select movies.id, movies.name, movies.description, avg(mr.rate) as avgRate
                                    from movies
                                             left outer join movie_rates mr on movies.id = mr.movie_id
                                    group by movies.id
                                    order by avgRate desc nulls last, movies.id
                                    offset :offset
                                    limit :pageSize
                        """)
                .bind("offset", page * pageSize)
                .bind("pageSize", pageSize)
                .map((row, rowMetadata) -> movieMapper.toRatedDto(Movie.builder()
                                .id(row.get(0, Long.class))
                                .name(row.get(1, String.class))
                                .description(row.get(2, String.class))
                                .build(),
                        List.of(),
                        row.get("avgRate", BigDecimal.class)))
                .all()
                .flatMap(md -> genreService.findAllByMovieId(md.id())
                        .collectList()
                        .map(genres -> new RatedMovieDto(md.id(),
                                md.name(),
                                md.description(),
                                genres.parallelStream().map(genreMapper::toDto).toList(),
                                md.rate())));
    }


    // could be made with movieRepository via custom query, but want to have rate here :)
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
    }

    public Mono<MovieDto> findById(Long id) {
        return movieService.findById(id)
                .onErrorResume(e -> Mono.empty())
                .flatMap(movie -> genreService.findAllByMovieId(movie.getId())
                        .collectList()
                        .map(genres -> movieMapper.toDto(movie, genres.parallelStream().map(genreMapper::toDto).toList())));
    }
}
