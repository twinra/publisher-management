package com.github.twinra.domain.service;

import com.github.twinra.domain.action.SearchPublishers;
import com.github.twinra.domain.action.UpdatePublishers;
import com.github.twinra.domain.gateway.PublishersGateway;
import com.github.twinra.domain.model.Publisher;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class PublishersService implements SearchPublishers, UpdatePublishers {
    private final PublishersGateway gateway;

    @Override
    public List<Publisher> findAll() {
        return gateway.getAll();
    }

    @Override
    public Optional<Publisher> findById(Publisher.Id id) {
        return gateway.getById(id);
    }

    @Override
    public Publisher.Id create(Publisher.Create request) {
        Publisher newPublisher = fromCreateRequest(request);
        return gateway.save(newPublisher).getId();
    }

    @Override
    public boolean update(Publisher.Id id, Publisher.Update request) {
        Optional<Publisher> found = gateway.getById(id);
        if (found.isPresent()) {
            Publisher updatedPublisher = fromUpdateRequest(found.get(), request);
            gateway.save(updatedPublisher);
        }
        return found.isPresent();
    }

    @Override
    public boolean delete(Publisher.Id id) {
        boolean found = gateway.existsById(id);
        if (found)
            gateway.deleteById(id);
        return found;
    }

    private static Publisher fromCreateRequest(Publisher.Create request) {
        return new Publisher(null, request.getName(), request.getContacts());
    }

    private static Publisher fromUpdateRequest(Publisher publisher, Publisher.Update request) {
        return new Publisher(publisher.getId(), publisher.getName(), request.getContacts());
    }

}
