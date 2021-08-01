package com.github.twinra.domain.service;

import com.github.twinra.domain.action.SearchPublishers;
import com.github.twinra.domain.action.UpdatePublishers;
import com.github.twinra.domain.gateway.PublishersGateway;

public class ActionsFactory {
    public static SearchPublishers searchPublishers(PublishersGateway gateway) {
        return new PublishersService(gateway);
    }

    public static UpdatePublishers updatePublishers(PublishersGateway gateway) {
        return new PublishersService(gateway);
    }
}
