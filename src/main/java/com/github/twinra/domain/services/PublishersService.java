package com.github.twinra.domain.services;

import com.github.twinra.domain.model.Publisher;
import com.github.twinra.domain.ports.provided.PublishersModifier;
import com.github.twinra.domain.ports.provided.PublishersReader;
import com.github.twinra.domain.ports.required.PublishersGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
class PublishersService implements PublishersReader, PublishersModifier {
    private final PublishersGateway gateway;

    @Override
    public Set<Publisher> getAll() {
        return gateway.getAll();
    }

    @Override
    public Optional<Publisher> getById(Publisher.Id id) {
        return gateway.getById(id);
    }

    @Override
    public Publisher.Id create(Publisher publisher) {
        return gateway.save(publisher).getId();
    }

    @Override
    public boolean update(Publisher.Id id, Publisher publisher) {
        boolean found = gateway.existsById(id);
        if (found)
            gateway.save(publisher.withId(id));
        return found;
    }

    @Override
    public boolean deleteById(Publisher.Id id) {
        boolean found = gateway.existsById(id);
        if (found)
            gateway.deleteById(id);
        return found;
    }
}
