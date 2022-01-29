package com.github.twinra.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Publisher {
    private final Id id;
    private final String name;
    private final String contacts;
    private final Status status;

    @Data
    public static class Id {
        private final long value;
    }

    @Data
    public static class Create {
        private final String name;
        private final String contacts;
    }

    @Data
    public static class Update {
        private final String contacts;
        private final Status status;
    }

    public enum Status {
        ACTIVE,
        INACTIVE
    }
}
