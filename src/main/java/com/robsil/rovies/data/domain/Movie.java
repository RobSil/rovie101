package com.robsil.rovies.data.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("movies")
public class Movie implements Persistable<Long> {

    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private String description;

    @Transient
    private List<Genre> genres;

    @Override
    public boolean isNew() {
        return id == null;
    }
}
