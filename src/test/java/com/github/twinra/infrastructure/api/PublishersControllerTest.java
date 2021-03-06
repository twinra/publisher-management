package com.github.twinra.infrastructure.api;

import com.github.twinra.domain.action.SearchPublishers;
import com.github.twinra.domain.action.UpdatePublishers;
import com.github.twinra.domain.model.Publisher;
import com.github.twinra.infrastructure.api.dto.CreatePublisherDto;
import com.github.twinra.infrastructure.api.dto.DataDto;
import com.github.twinra.infrastructure.api.dto.PublisherDto;
import com.github.twinra.infrastructure.api.dto.UpdatePublisherDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PublishersControllerTest {

    @InjectMocks
    PublishersController controller;

    @Mock
    SearchPublishers reader;

    @Mock
    UpdatePublishers modifier;

    private long id;

    @BeforeEach
    void setUp() {
        id = new Random().nextLong();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @AfterEach
    void tearDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void getAll_returnsPublishers_providedByReader() {
        List<Publisher> publishers = List.of(
                newPublisher(123, "ABC", "EMAIL", Publisher.Status.ACTIVE),
                newPublisher(456, "XYZ", "PHONE", Publisher.Status.INACTIVE)
        );
        given(reader.findAll()).willReturn(publishers);

        //when
        DataDto<PublisherDto> response = controller.getAll();

        //then
        assertThat(response.getData()).hasSize(2).extracting(PublisherDto::getId).contains(123L, 456L);
    }

    @Test
    void getById_returnsPublisher_foundByReader() {
        Publisher foundPublisher = newPublisher(id, "ACME", "noreply@acme.com", Publisher.Status.ACTIVE);
        given(reader.findById(foundPublisher.getId())).willReturn(Optional.of(foundPublisher));

        //when
        ResponseEntity<PublisherDto> response = controller.getById(id);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).satisfies(dto -> {
            assertThat(dto.getId()).isEqualTo(foundPublisher.getId().getValue());
            assertThat(dto.getName()).isEqualTo(foundPublisher.getName());
            assertThat(dto.getContacts()).isEqualTo(foundPublisher.getContacts());
            assertThat(dto.getStatus()).isEqualTo(foundPublisher.getStatus());
        });
    }

    @Test
    public void getById_respondsNotFound_ifReaderFindsNothing() {
        Publisher.Id publisherId = new Publisher.Id(id);
        given(reader.findById(publisherId)).willReturn(Optional.empty());

        //when
        ResponseEntity<PublisherDto> response = controller.getById(id);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.hasBody()).isFalse();
    }

    @Test
    void create_returnsId_providedByModifier() {
        CreatePublisherDto dto = new CreatePublisherDto("NAME", "EMAIL");
        Publisher.Id publisherId = new Publisher.Id(id);

        given(modifier.create(any(Publisher.Create.class))).willReturn(publisherId);

        //when
        ResponseEntity<Void> response = controller.create(dto);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.hasBody()).isFalse();
        assertThat(response.getHeaders().getLocation()).hasPath("/" + publisherId.getValue());
    }

    @Test
    void update_respondsNotFound_ifModifierDidNotFindObjectToUpdate() {
        UpdatePublisherDto dto = new UpdatePublisherDto("aaa@aaa.aaa", Publisher.Status.ACTIVE);

        given(modifier.update(eq(new Publisher.Id(id)), any(Publisher.Update.class))).willReturn(false);

        //when
        ResponseEntity<Void> response = controller.update(id, dto);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.hasBody()).isFalse();
    }

    @Test
    void update_respondsNoContent_ifModifierHasFoundObjectToUpdate() {
        UpdatePublisherDto dto = new UpdatePublisherDto("aaa@aaa.aaa", Publisher.Status.INACTIVE);

        given(modifier.update(eq(new Publisher.Id(id)), any(Publisher.Update.class))).willReturn(true);

        //when
        ResponseEntity<Void> response = controller.update(id, dto);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.hasBody()).isFalse();
    }

    @Test
    void deleteById_respondsNotFound_ifModifierDidNotFindObjectToDelete() {
        given(modifier.delete(new Publisher.Id(id))).willReturn(false);

        //when
        ResponseEntity<Void> response = controller.delete(id);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.hasBody()).isFalse();
    }


    @Test
    void deleteById_respondsNoContent_ifModifierHasFoundObjectToDelete() {
        given(modifier.delete(new Publisher.Id(id))).willReturn(true);

        //when
        ResponseEntity<Void> response = controller.delete(id);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.hasBody()).isFalse();
    }

    private static Publisher newPublisher(long id, String name, String contacts, Publisher.Status status) {
        return Publisher.builder()
                .id(new Publisher.Id(id))
                .name(name)
                .contacts(contacts)
                .status(status)
                .build();
    }
}