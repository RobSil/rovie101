package com.robsil.rovies.model.movie;

import com.robsil.rovies.model.genre.GenreDto;

import java.math.BigDecimal;
import java.util.List;

public record RatedMovieDto(
        Long id,
        String name,
        String description,
        List<GenreDto> genres,
        BigDecimal rate
) {
}
