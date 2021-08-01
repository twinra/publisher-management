package com.github.twinra.domain.model;

import lombok.Data;

@Data
public class Publisher {
    private final Id id;
    private final String name;
    private final String email;

    public Publisher withId(Id id) {
        return new Publisher(id, this.name, this.email);
    }

    @Data
    public static class Id {
        private final long value;
    }

    @Data
    public static class CreateRequest {
        private final String name;
        private final String email;
    }

    @Data
    public static class UpdateRequest {
        private final String email;
    }
}
