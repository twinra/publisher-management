package com.github.twinra.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Publisher {
    private final Long id;
    private final String name;
    private final String email;
}
