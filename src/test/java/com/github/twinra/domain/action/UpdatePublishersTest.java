package com.github.twinra.domain.action;

import com.github.twinra.domain.gateway.PublishersGateway;
import com.github.twinra.domain.model.Publisher;
import com.github.twinra.domain.service.ActionsFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UpdatePublishersTest {

    @Mock
    private PublishersGateway gateway;

    private UpdatePublishers action;

    @BeforeEach
    void setUp() {
        action = ActionsFactory.updatePublishers(gateway);
    }

    @Test
    void create_savesPublisherWithPassedParametersAndActiveStatus() {
        Publisher.Create request = new Publisher.Create("NAME", "EMAIL");

        given(gateway.save(any(Publisher.class))).willReturn(publisherWithId(127));

        //when
        action.create(request);

        //then
        ArgumentCaptor<Publisher> captor = ArgumentCaptor.forClass(Publisher.class);
        verify(gateway).save(captor.capture());
        assertThat(captor.getValue()).satisfies(publisher -> {
            assertThat(publisher.getName()).isEqualTo("NAME");
            assertThat(publisher.getContacts()).isEqualTo("EMAIL");
            assertThat(publisher.getStatus()).isEqualTo(Publisher.Status.ACTIVE);
        });
    }

    @Test
    void create_returnsIdSavedInGateway() {
        Publisher.Create request = new Publisher.Create("NAME", "EMAIL");

        long idValue = 8971263;
        given(gateway.save(any(Publisher.class))).willReturn(publisherWithId(idValue));

        //when
        Publisher.Id publisherId = action.create(request);

        //then
        assertThat(publisherId.getValue()).isEqualTo(idValue);
    }


    @Test
    void update_doesNothing_ifPublisherWithPassedIdDoesNotExist() {
        Publisher.Update request = new Publisher.Update("NEW_EMAIL", Publisher.Status.ACTIVE);

        Publisher.Id id = new Publisher.Id(916128);
        given(gateway.getById(id)).willReturn(Optional.empty());

        //when
        action.update(id, request);

        //then
        verify(gateway, never()).save(any(Publisher.class));
    }

    @Test
    void update_modifiesPublisherWithPassedId_ifItExists() {
        Publisher.Update request = new Publisher.Update("NEW_EMAIL", Publisher.Status.INACTIVE);

        Publisher.Id id = new Publisher.Id(916128);
        given(gateway.getById(id)).willReturn(Optional.of(Publisher.builder()
                .id(id)
                .name("NAME")
                .contacts("EMAIL")
                .status(Publisher.Status.ACTIVE)
                .build())
        );

        //when
        action.update(id, request);

        //then
        ArgumentCaptor<Publisher> captor = ArgumentCaptor.forClass(Publisher.class);
        verify(gateway).save(captor.capture());
        assertThat(captor.getValue()).satisfies(publisher -> {
            assertThat(publisher.getName()).isEqualTo("NAME");
            assertThat(publisher.getContacts()).isEqualTo("NEW_EMAIL");
            assertThat(publisher.getStatus()).isEqualTo(Publisher.Status.INACTIVE);
        });
    }

    @Test
    void update_returnsFlag_indicatingIfPublisherToUpdateExists() {
        Publisher.Update request = new Publisher.Update("NEW_EMAIL", Publisher.Status.ACTIVE);

        Publisher.Id id1 = new Publisher.Id(561253);
        Publisher.Id id2 = new Publisher.Id(428743);
        given(gateway.getById(id1)).willReturn(Optional.of(publisherWithId(id1.getValue())));
        given(gateway.getById(id2)).willReturn(Optional.empty());

        //expect
        assertThat(action.update(id1, request)).isTrue();
        assertThat(action.update(id2, request)).isFalse();

    }

    @Test
    void delete_doesNothing_ifPublisherWithPassedIdDoesNotExist() {
        Publisher.Id id = new Publisher.Id(128743);

        given(gateway.existsById(id)).willReturn(false);

        //when
        action.delete(id);

        //then
        verify(gateway, never()).deleteById(id);
    }

    @Test
    void delete_deletesPublisherWithPassedIdFromGateway_ifItExists() {
        Publisher.Id id = new Publisher.Id(761253);

        given(gateway.existsById(id)).willReturn(true);

        //when
        action.delete(id);

        //then
        verify(gateway).deleteById(any(Publisher.Id.class));
    }

    @Test
    void delete_returnsFlag_indicatingIfPublisherToDeleteExists() {
        Publisher.Id id1 = new Publisher.Id(761253);
        Publisher.Id id2 = new Publisher.Id(128743);
        given(gateway.existsById(id1)).willReturn(true);
        given(gateway.existsById(id2)).willReturn(false);

        //expect
        assertThat(action.delete(id1)).isTrue();
        assertThat(action.delete(id2)).isFalse();
    }


    private static Publisher publisherWithId(long id) {
        return Publisher.builder()
                .id(new Publisher.Id(id))
                .build();
    }
}
