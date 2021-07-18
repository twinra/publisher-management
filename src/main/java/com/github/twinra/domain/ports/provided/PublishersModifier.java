package com.github.twinra.domain.ports.provided;

import com.github.twinra.domain.model.Publisher;

public interface PublishersModifier {
    Publisher.Id create(Publisher publisher);

    boolean update(Publisher.Id id, Publisher publisher);

    boolean deleteById(Publisher.Id id);
}
