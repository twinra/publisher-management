package com.github.twinra.domain.model;

import lombok.Data;

import java.util.Set;

@Data
public class Book {
    private final ISBN isbn;
    private final String title;
    private final Set<String> authors;
    private final Publisher publisher;

    @Data
    public static class ISBN {
        private final long value;
    }
}
