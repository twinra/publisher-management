package com.github.twinra.domain.gateways;

import com.github.twinra.domain.model.Publisher;

import java.util.Optional;
import java.util.Set;

public interface PublishersGateway {
    Set<Publisher> getAll();

    Optional<Publisher> getById(long id);

    Publisher save(Publisher publisher);

    void deleteById(long id);
}
