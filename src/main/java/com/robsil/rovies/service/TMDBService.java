package com.robsil.rovies.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robsil.rovies.data.domain.Genre;
import com.robsil.rovies.data.domain.Movie;
import com.robsil.rovies.data.domain.MovieGenre;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
        httpClient = HttpClient.newBuilder().build();
    }

    //    @Transactional
    public List<Movie> getTopRatedMovies(int page) {
        Assert.isTrue(page > 0, "page should be higher than zero");
//        tmdbUrl + "/3/movie/top_rated?page=" + page
        var request = HttpRequest.newBuilder()
                .uri(URI.create(tmdbUrl + "/3/movie/top_rated?page=" + page))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        Map<String, Object> map;
        try {
            map = objectMapper.readValue(response.body(), new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return ((List<Map<String, Object>>) map.get("results")).stream().map(movieMap -> {
                    try {
                        var genres = genreService.findAllGenresByTmdbIdIn(((List<Integer>) movieMap.get("genre_ids")).stream()
                                .map(Integer::longValue)
                                .toList()).collectList().toFuture().get();
                        var movie = movieService.saveEntity(Movie.builder()
                                .name((String) movieMap.get("title"))
                                .description((String) movieMap.get("overview"))
                                .build()).toFuture().get();
                        genres.forEach(genre -> {
                            try {
                                movieGenreService.save(MovieGenre.builder()
                                        .movieId(movie.getId())
                                        .genreId(genre.getId())
                                        .build()).toFuture().get();
                            } catch (Exception e) {
                                log.info("exception occurred movieGenre save forEach. " + e.getMessage());
                            }
                        });
                        return movie;
                    } catch (Exception e) {
                        log.info("exception occurred ((List<Map<String, Object>>) map.get(\"results\")). " + e.getMessage());
                        return null;
                    }
                })
                .toList();
    }

    @SneakyThrows
    public List<Genre> getGenres() {

        var request = HttpRequest.newBuilder()
                .uri(URI.create(tmdbUrl + "/3/genre/movie/list"))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        var body = objectMapper.readValue(response.body(), new TypeReference<HashMap<String, Object>>() {
        });
        var result = ((ArrayList<HashMap<String, Object>>) body.get("genres"))
                .stream()
                .map(genre -> {
                    try {
                        return genreService.saveEntity(Genre.builder().name((String) genre.get("name"))
                                .tmdbId(((Integer) genre.get("id")).longValue())
                                .build()).toFuture().get();
                    } catch (Exception e) {
                        log.info("exception occurred. " + e.getMessage());
                        return null;
                    }
                })
                .toList();

        log.info("breakpoint");
        return result;
    }
}
