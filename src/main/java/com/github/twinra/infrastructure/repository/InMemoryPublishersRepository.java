package com.github.twinra.infrastructure.repository;

import com.github.twinra.domain.gateway.PublishersGateway;
import com.github.twinra.domain.model.Publisher;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryPublishersRepository implements PublishersGateway {
    private static final int ID_INITIAL_VALUE = 0;
    private static final int ID_INCREMENT = 1;
    private final Map<Publisher.Id, Publisher> publishersById = new HashMap<>();

    @Override
    public List<Publisher> getAll() {
        return new LinkedList<>(publishersById.values());
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
        Publisher toSave = generateIdIfEmpty(publisher);
        publishersById.put(toSave.getId(), toSave);
        return publishersById.get(toSave.getId());
    }

    @Override
    public void deleteById(Publisher.Id id) {
        publishersById.remove(id);
    }


    private Publisher.Id generateNewId() {
        long nextId = publishersById.keySet().stream()
                .mapToLong(Publisher.Id::getValue)
                .max().orElse(ID_INITIAL_VALUE) + ID_INCREMENT;
        return new Publisher.Id(nextId);
    }

    private Publisher generateIdIfEmpty(Publisher publisher) {
        return publisher.getId() != null ? publisher : Publisher.builder()
                .id(generateNewId())
                .name(publisher.getName())
                .contacts(publisher.getContacts())
                .status(publisher.getStatus())
                .build();
    }
}
