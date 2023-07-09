package com.robsil.rovies.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robsil.rovies.data.domain.Genre;
import com.robsil.rovies.data.domain.Movie;
import com.robsil.rovies.data.domain.MovieGenre;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import reactor.util.function.Tuples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TMDBService {

    @Value("${tmdb.access-token}")
    private String accessToken;
    private static final String tmdbUrl = "https://api.themoviedb.org";

    private HttpClient httpClient;

    private final ObjectMapper objectMapper;
    private final MovieService movieService;
    private final GenreService genreService;
    private final MovieGenreService movieGenreService;

    @PostConstruct
    void init() {
        httpClient = HttpClient.create()
                .baseUrl(tmdbUrl)
                .headers(headers -> {
                    headers.add("Authorization", "Bearer " + accessToken);
                    headers.add("accept", "application/json");
                });
    }

    //    @Transactional
    public Flux<MovieGenre> getTopRatedMovies(int page) {
        Assert.isTrue(page > 0, "page should be higher than zero");
//        var flux = Flux.fromIterable(List.of(1, 2, 3));
//        var mono = Mono.just(4);
//        flux.zipWith
        var result = httpClient.get()
                .uri("/3/movie/top_rated?page=" + page)
                .responseContent()
                .aggregate()
                .asString()
                .map(body -> {
                    try {
                        return objectMapper.readValue(body, new TypeReference<HashMap<String, Object>>() {
                        });
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMapIterable(map -> (ArrayList<HashMap<String, Object>>) map.get("results"));
        log.info("breakpoint");
        return processMovieResponse(result);
    }

    public Flux<MovieGenre> processMovieResponse(Flux<Map<String, Object>> response) {
        var result = response
                .flatMap(map -> {
                    var genres =
                            genreService.findAllGenresByTmdbIdIn(((List<Integer>) map.get("genre_ids")).stream()
                                    .map(Integer::longValue)
                                    .toList());

                    genres.subscribe(genre -> log.info("genres: " + genre.toString()));

                    var movie = movieService.saveEntity(Movie.builder()
                            .name((String) map.get("title"))
                            .description((String) map.get("overview"))
                            .build());
                    var tuples = genres.flatMap(genre -> movie.map(movieItem -> Tuples.of(genre, movieItem)));
                    tuples.subscribe(tuple -> log.info("tuples sub: " + tuple.toString()));
                    tuples.hasElements().subscribe(bool -> log.info("tuple hasElements: " + bool.toString()));
                    return tuples;
                })
                .flatMap(tuple -> {
                    var movieGenre = movieGenreService.save(MovieGenre.builder()
                            .genreId(tuple.getT1().getId())
                            .movieId(tuple.getT2().getId())
                            .build());
                    return movieGenre;
                });

        return result;
    }

    public Flux<Genre> getGenres() {
        var result = httpClient.get()
                .uri("/3/genre/movie/list")
                .responseContent()
                .aggregate()
                .asString()
                .map(body -> {
                    try {
                        return objectMapper.readValue(body, new TypeReference<HashMap<String, Object>>() {
                        });
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMapIterable(map -> (ArrayList<HashMap<String, Object>>) map.get("genres"))
                .flatMap(map -> {
                    var genre = Genre.builder().name((String) map.get("name"))
                            .tmdbId(((Integer) map.get("id")).longValue())
                            .build();
                    return genreService.saveEntity(genre);
                });

        log.info("breakpoint");
        return result;
    }
}
