package com.github.twinra.domain.actions;

import com.github.twinra.domain.model.Publisher;

public interface UpdatePublishers {
    Publisher.Id create(Publisher.CreateRequest request);

    boolean update(Publisher.Id id, Publisher.UpdateRequest request);

    boolean delete(Publisher.Id id);
}
