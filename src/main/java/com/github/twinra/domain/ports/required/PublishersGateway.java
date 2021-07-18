package com.github.twinra.domain.ports.required;

import com.github.twinra.domain.model.Publisher;

import java.util.Optional;
import java.util.Set;

public interface PublishersGateway {
    Set<Publisher> getAll();

    boolean existsById(Publisher.Id id);

    Optional<Publisher> getById(Publisher.Id id);

    Publisher save(Publisher publisher);

    void deleteById(Publisher.Id id);
}
