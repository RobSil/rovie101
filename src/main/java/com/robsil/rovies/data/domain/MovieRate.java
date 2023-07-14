package com.robsil.rovies.data.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("movie_rates")
public class MovieRate implements Persistable<Long> {

    @Id
    private Long id;
    @Column("user_id")
    private Long userId;
    @Column("movie_id")
    private Long movieId;
    @Column
    private BigDecimal rate;

    @Override
    public boolean isNew() {
        return id == null;
    }
}
