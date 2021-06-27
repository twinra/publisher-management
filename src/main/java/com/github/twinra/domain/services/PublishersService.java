package com.github.twinra.domain.services;

import com.github.twinra.domain.gateways.PublishersGateway;
import com.github.twinra.domain.model.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PublishersService {
    private final PublishersGateway gateway;

    public Set<Publisher> getAll() {
        return gateway.getAll();
    }

    public Optional<Publisher> getById(long id) {
        return gateway.getById(id);
    }

    public Publisher save(Publisher publisher) {
        return gateway.save(publisher);
    }

    public void delete(Publisher publisher) {
        gateway.deleteById(publisher.getId());
    }
}
