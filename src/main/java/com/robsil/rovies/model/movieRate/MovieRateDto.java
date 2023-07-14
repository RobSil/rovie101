package com.robsil.rovies.model.movieRate;

import java.math.BigDecimal;

public record MovieRateDto(
        Long id,
        Long userId,
        Long movieId,
        BigDecimal rate
) {
}
