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
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("roles")
public class Role implements Persistable<Long> {

    @Id
    private Long id;
    @Column
    private String name;

    @Override
    public boolean isNew() {
        return id == null;
    }
}
