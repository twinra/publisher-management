package com.github.twinra.infrastructure.config;

import com.github.twinra.domain.action.SearchPublishers;
import com.github.twinra.domain.action.UpdatePublishers;
import com.github.twinra.domain.gateway.PublishersGateway;
import com.github.twinra.domain.service.ActionsFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActionsConfig {
    @Bean
    public UpdatePublishers updatePublishers(PublishersGateway publishersGateway) {
        return ActionsFactory.updatePublishers(publishersGateway);
    }

    @Bean
    public SearchPublishers searchPublishers(PublishersGateway publishersGateway) {
        return ActionsFactory.searchPublishers(publishersGateway);
    }
}
