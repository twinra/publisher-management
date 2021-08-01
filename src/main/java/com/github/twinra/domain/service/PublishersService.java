package com.github.twinra.domain.service;

import com.github.twinra.domain.actions.SearchPublishers;
import com.github.twinra.domain.actions.UpdatePublishers;
import com.github.twinra.domain.gateways.PublishersGateway;
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
    public Publisher.Id create(Publisher.CreateRequest request) {
        Publisher newPublisher = fromCreateRequest(request);
        return gateway.save(newPublisher).getId();
    }

    @Override
    public boolean update(Publisher.Id id, Publisher.UpdateRequest request) {
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

    private static Publisher fromCreateRequest(Publisher.CreateRequest request) {
        return new Publisher(null, request.getName(), request.getEmail());
    }

    private static Publisher fromUpdateRequest(Publisher publisher, Publisher.UpdateRequest request) {
        return new Publisher(publisher.getId(), publisher.getName(), request.getEmail());
    }

}
