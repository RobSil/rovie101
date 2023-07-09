package com.robsil.rovies.model.movie;

import com.robsil.rovies.model.genre.GenreDto;

import java.util.List;

public record MovieDto(
        Long id,
        String name,
        String description,
        List<GenreDto> genres
) {
}
