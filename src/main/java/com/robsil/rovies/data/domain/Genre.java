package com.robsil.rovies.data.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table("genres")
public class Genre implements Persistable<Long> {
    @Id
    private Long id;
    @Column
    private String name;
    @Column("tmdb_id")
    private Long tmdbId;

    @Override
    public boolean isNew() {
        return id == null;
    }
}
