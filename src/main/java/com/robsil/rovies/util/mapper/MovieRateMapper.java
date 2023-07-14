package com.robsil.rovies.util.mapper;

import com.robsil.rovies.data.domain.MovieRate;
import com.robsil.rovies.model.movieRate.MovieRateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieRateMapper {
    MovieRateDto toDto(MovieRate movieRate);

}
