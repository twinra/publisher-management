package com.github.twinra.domain.action;

import com.github.twinra.domain.model.Publisher;

import java.util.List;
import java.util.Optional;

public interface SearchPublishers {
    Optional<Publisher> findById(Publisher.Id id);

    List<Publisher> findAll();
}
