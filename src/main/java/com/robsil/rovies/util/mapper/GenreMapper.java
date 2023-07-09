package com.robsil.rovies.util.mapper;

import com.robsil.rovies.data.domain.Genre;
import com.robsil.rovies.model.genre.GenreDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreDto toDto(Genre genre);

}
