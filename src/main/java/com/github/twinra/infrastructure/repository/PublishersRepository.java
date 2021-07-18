package com.github.twinra.infrastructure.repository;

import com.github.twinra.domain.model.Publisher;
import com.github.twinra.domain.ports.required.PublishersGateway;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class PublishersRepository implements PublishersGateway {
    private static final int ID_INITIAL_VALUE = 0;
    private static final int ID_INCREMENT = 1;
    private final Map<Publisher.Id, Publisher> publishersById = Set.of(
            new Publisher(new Publisher.Id(11), "ABC", "admin@abc.com"),
            new Publisher(new Publisher.Id(22), "XYZ", "xyz@xyz.org"),
            new Publisher(new Publisher.Id(33), "QWERTY", "asdfgh@qwerty.io")
    ).stream().collect(Collectors.toMap(Publisher::getId, p -> p));

    @Override
    public Set<Publisher> getAll() {
        return new HashSet<>(publishersById.values());
    }

    @Override
    public boolean existsById(Publisher.Id id) {
        return publishersById.containsKey(id);
    }

    @Override
    public Optional<Publisher> getById(Publisher.Id id) {
        return Optional.ofNullable(publishersById.get(id));
    }

    @Override
    public Publisher save(Publisher publisher) {
        Publisher.Id id = Optional.ofNullable(publisher.getId()).orElseGet(this::generateNewId);
        publishersById.put(id, publisher.withId(id));
        return publishersById.get(id);
    }

    @Override
    public void deleteById(Publisher.Id id) {
        publishersById.remove(id);
    }


    private Publisher.Id generateNewId() {
        long nextId = publishersById.keySet().stream()
                .mapToLong(id -> id.getValue())
                .max().orElse(ID_INITIAL_VALUE) + ID_INCREMENT;
        return new Publisher.Id(nextId);
    }
}
