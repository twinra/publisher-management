package com.github.twinra.domain.action;

import com.github.twinra.domain.gateway.PublishersGateway;
import com.github.twinra.domain.model.Publisher;
import com.github.twinra.domain.service.ActionsFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SearchPublishersTest {

    @Mock
    private PublishersGateway gateway;

    private SearchPublishers action;

    @BeforeEach
    void setUp() {
        action = ActionsFactory.searchPublishers(gateway);
    }

    @Test
    void findById_returnsPublisherFoundInGateway() {
        Publisher.Id id1 = new Publisher.Id(8710539);
        Publisher.Id id2 = new Publisher.Id(1260712);
        Publisher publisher = new Publisher(id1, "NAME", "EMAIL");

        given(gateway.getById(id1)).willReturn(Optional.of(publisher));
        given(gateway.getById(id2)).willReturn(Optional.empty());

        //expect
        assertThat(action.findById(id1)).contains(publisher);
        assertThat(action.findById(id2)).isEmpty();

    }

    @Test
    void findAll_returnsPublisherFoundInGateway() {
        Publisher publisher1 = new Publisher(new Publisher.Id(1111), "NAME1", "EMAIL1");
        Publisher publisher2 = new Publisher(new Publisher.Id(2222), "NAME2", "EMAIL2");

        given(gateway.getAll()).willReturn(List.of(publisher1, publisher2));

        //expect
        assertThat(action.findAll()).containsOnly(publisher2, publisher1);
    }
}