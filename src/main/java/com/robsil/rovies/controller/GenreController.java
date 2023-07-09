package com.robsil.rovies.controller;

import com.robsil.rovies.data.domain.Genre;
import com.robsil.rovies.service.GenreService;
import com.robsil.rovies.service.TMDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final TMDBService tmdbService;
    private final GenreService genreService;

    @GetMapping("/parseGenres")
    List<Genre> parseGenres() {
        return tmdbService.getGenres();
    }

}
