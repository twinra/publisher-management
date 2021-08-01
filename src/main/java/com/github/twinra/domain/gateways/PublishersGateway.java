package com.github.twinra.domain.gateways;

import com.github.twinra.domain.model.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublishersGateway {
    List<Publisher> getAll();

    boolean existsById(Publisher.Id id);

    Optional<Publisher> getById(Publisher.Id id);

    Publisher save(Publisher publisher);

    void deleteById(Publisher.Id id);
}
