package com.robsil.rovies.model.movierate;

import java.math.BigDecimal;

public record SimpleMovieRateCreateRequest(
        BigDecimal rate
) {
}
