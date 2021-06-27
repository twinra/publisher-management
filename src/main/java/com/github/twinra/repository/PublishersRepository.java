package com.github.twinra.repository;

import com.github.twinra.domain.gateways.PublishersGateway;
import com.github.twinra.domain.model.Publisher;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class PublishersRepository implements PublishersGateway {
    private final Map<Long, Publisher> publishersById = Set.of(
            new Publisher(11L, "ABC", "admin@abc.com"),
            new Publisher(22L, "XYZ", "xyz@xyz.org"),
            new Publisher(33L, "QWERTY", "asdfgh@qwerty.io")
    ).stream().collect(Collectors.toMap(Publisher::getId, p -> p));

    @Override
    public Set<Publisher> getAll() {
        return new HashSet<>(publishersById.values());
    }

    @Override
    public Optional<Publisher> getById(long id) {
        return Optional.ofNullable(publishersById.get(id));
    }

    @Override
    public Publisher save(Publisher publisher) {
        Long id = publisher.getId();
        publishersById.put(id, publisher);
        return publishersById.get(id);
    }

    @Override
    public void deleteById(long id) {
        publishersById.remove(id);
    }
}
