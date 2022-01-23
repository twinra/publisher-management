package com.github.twinra.domain.model;

import lombok.Data;

@Data
public class Book {
    private final ISBN isbn;
    private final String title;
    private final String author;
    private final Publisher publisher;

    @Data
    public static class ISBN {
        private final long value;
    }
}
