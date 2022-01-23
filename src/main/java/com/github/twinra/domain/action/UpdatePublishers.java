package com.github.twinra.domain.action;

import com.github.twinra.domain.model.Publisher;

public interface UpdatePublishers {
    Publisher.Id create(Publisher.Create request);

    boolean update(Publisher.Id id, Publisher.Update request);

    boolean delete(Publisher.Id id);
}
