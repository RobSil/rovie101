package com.robsil.rovies.util.mapper;

import com.robsil.rovies.data.domain.Movie;
import com.robsil.rovies.model.genre.GenreDto;
import com.robsil.rovies.model.movie.MovieDto;
import com.robsil.rovies.model.movie.RatedMovieDto;
import com.robsil.rovies.model.movieRate.MovieRateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(source = "genres", target = "genres")
    MovieDto toDto(Movie movie, List<GenreDto> genres);

    @Mapping(source = "genres", target = "genres")
    @Mapping(source = "rate", target = "rate")
    RatedMovieDto toRatedDto(Movie movie, List<GenreDto> genres, BigDecimal rate);

}
