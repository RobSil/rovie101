package com.robsil.rovies.model.movierate;

import java.math.BigDecimal;

public record MovieRateCreateRequest(
        Long movieId,
        BigDecimal rate
) {
    public MovieRateCreateRequest(Long movieId, SimpleMovieRateCreateRequest request) {
        this(movieId, request.rate());
    }
}
