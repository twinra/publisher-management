package com.github.twinra.domain.service;

import com.github.twinra.domain.actions.SearchPublishers;
import com.github.twinra.domain.actions.UpdatePublishers;
import com.github.twinra.domain.gateways.PublishersGateway;

public class ActionsFactory {
    public static SearchPublishers searchPublishers(PublishersGateway gateway) {
        return new PublishersService(gateway);
    }

    public static UpdatePublishers updatePublishers(PublishersGateway gateway) {
        return new PublishersService(gateway);
    }
}
