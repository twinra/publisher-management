package com.github.twinra.domain.ports.provided;

import com.github.twinra.domain.model.Publisher;

import java.util.Optional;
import java.util.Set;

public interface PublishersReader {
    Optional<Publisher> getById(Publisher.Id id);

    Set<Publisher> getAll();
}
