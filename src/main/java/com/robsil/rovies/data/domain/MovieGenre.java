package com.robsil.rovies.data.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("movie_genre")
public class MovieGenre {

    @Column("movie_id")
    private Long movieId;
    @Column("genre_id")
    private Long genreId;

}
