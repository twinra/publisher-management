package com.github.twinra.domain.model;

import lombok.Data;

import java.util.Set;

@Data
public class Book {
    private final Long isbn;
    private final String title;
    private final Set<String> authors;
    private final Publisher publisher;
}
