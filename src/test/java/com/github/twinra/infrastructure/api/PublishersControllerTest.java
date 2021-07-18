package com.github.twinra.infrastructure.api;

import com.github.twinra.domain.model.Publisher;
import com.github.twinra.domain.ports.provided.PublishersModifier;
import com.github.twinra.domain.ports.provided.PublishersReader;
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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublishersControllerTest {

    @InjectMocks
    PublishersController controller;

    @Mock
    PublishersReader reader;

    @Mock
    PublishersModifier modifier;

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
        Set<Publisher> publishers = Set.of(
                new Publisher(new Publisher.Id(123), "NAME", "EMAIL"),
                new Publisher(new Publisher.Id(456), "ANOTHER_NAME", "ANOTHER_EMAIL")
        );
        when(reader.getAll()).thenReturn(publishers);

        //when
        List<PublisherDto> response = controller.getAll();

        //then
        assertThat(response).hasSize(2).extracting(PublisherDto::getId).contains(123L, 456L);
    }

    @Test
    void getById_returnsPublisher_foundByReader() {
        Publisher foundPublisher = new Publisher(new Publisher.Id(id), "ACME", "noreply@acme.com");
        when(reader.getById(foundPublisher.getId())).thenReturn(Optional.of(foundPublisher));

        //when
        ResponseEntity<PublisherDto> response = controller.getById(id);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).satisfies(dto -> {
            assertThat(dto.getId()).isEqualTo(foundPublisher.getId().getValue());
            assertThat(dto.getName()).isEqualTo(foundPublisher.getName());
            assertThat(dto.getEmail()).isEqualTo(foundPublisher.getEmail());
        });
    }

    @Test
    public void getById_respondsNotFound_ifReaderFindsNothing() {
        Publisher.Id publisherId = new Publisher.Id(id);
        when(reader.getById(publisherId)).thenReturn(Optional.empty());

        //when
        ResponseEntity<PublisherDto> response = controller.getById(id);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.hasBody()).isFalse();
    }

    @Test
    void create_returnsId_providedByModifier() {
        PublisherDto dto = new PublisherDto(null, "NAME", "EMAIL");
        Publisher.Id publisherId = new Publisher.Id(id);

        when(modifier.create(any(Publisher.class))).thenReturn(publisherId);

        //when
        ResponseEntity<Void> response = controller.create(dto);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.hasBody()).isFalse();
        assertThat(response.getHeaders().getLocation()).hasPath("/" + publisherId.getValue());
    }

    @Test
    void update_respondsNotFound_ifModifierDidNotFindObjectToUpdate() {
        PublisherDto dto = new PublisherDto(null, "AAA", "aaa@aaa.aaa");

        when(modifier.update(eq(new Publisher.Id(id)), any(Publisher.class))).thenReturn(false);

        //when
        ResponseEntity<Void> response = controller.update(id, dto);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.hasBody()).isFalse();
    }

    @Test
    void update_respondsNoContent_ifModifierHasFoundObjectToUpdate() {
        PublisherDto dto = new PublisherDto(null, "AAA", "aaa@aaa.aaa");

        when(modifier.update(eq(new Publisher.Id(id)), any(Publisher.class))).thenReturn(true);

        //when
        ResponseEntity<Void> response = controller.update(id, dto);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.hasBody()).isFalse();
    }

    @Test
    void deleteById_respondsNotFound_ifModifierDidNotFindObjectToDelete() {
        when(modifier.deleteById(new Publisher.Id(id))).thenReturn(false);

        //when
        ResponseEntity<Void> response = controller.delete(id);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.hasBody()).isFalse();
    }


    @Test
    void deleteById_respondsNoContent_ifModifierHasFoundObjectToDelete() {
        when(modifier.deleteById(new Publisher.Id(id))).thenReturn(true);

        //when
        ResponseEntity<Void> response = controller.delete(id);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.hasBody()).isFalse();
    }

}