package com.robsil.rovies.model.movieRate;

import java.math.BigDecimal;

public record MovieRateCreateRequest(
        Long movieId,
        BigDecimal rate
) {
}
