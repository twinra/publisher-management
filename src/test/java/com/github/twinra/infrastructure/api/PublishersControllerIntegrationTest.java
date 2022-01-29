package com.github.twinra.infrastructure.api;

import com.github.twinra.domain.model.Publisher;
import com.github.twinra.infrastructure.api.dto.CreatePublisherDto;
import com.github.twinra.infrastructure.api.dto.DataDto;
import com.github.twinra.infrastructure.api.dto.PublisherDto;
import com.github.twinra.infrastructure.api.dto.UpdatePublisherDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class PublishersControllerIntegrationTest extends ControllerIntegrationTest {

    @AfterEach
    void tearDown() {
        getAllPublishers().forEach(p -> deletePublisher(p.getId()));
    }

    @Test
    public void createPublisherAndFindItById() {
        CreatePublisherDto publisher = new CreatePublisherDto("TWO", "Tel: 012345, Fax: 012345-00");
        long id = createPublisher(publisher);

        // expect
        assertThat(getPublisherById(id)).satisfies(dto -> {
            assertThat(dto.getId()).isEqualTo(id);
            assertThat(dto.getName()).isEqualTo(publisher.getName());
            assertThat(dto.getContacts()).isEqualTo(publisher.getContacts());
        });
    }

    @Test
    public void updatePublisherContactsById() {
        CreatePublisherDto createRequest = new CreatePublisherDto("THREE", "Address: Berlin, Alexander platz, 1");
        long id = createPublisher(createRequest);
        PublisherDto publisher = getPublisherById(id);

        //when
        UpdatePublisherDto updateRequest = new UpdatePublisherDto(
                publisher.getContacts().replace("Berlin", "Spandau").replace("Alexander", "Alexandra"),
                publisher.getStatus()
        );
        updatePublisher(id, updateRequest);

        //then
        assertThat(getPublisherById(id)).satisfies(p -> {
            assertThat(p.getName()).isEqualTo(createRequest.getName());
            assertThat(p.getContacts()).isEqualTo(updateRequest.getContacts());
        });
    }

    @Test
    public void createPublisherAndDeleteItById() {
        CreatePublisherDto publisher = new CreatePublisherDto("ONE", "email: two@one.com");
        long id = createPublisher(publisher);

        // when
        deletePublisher(id);

        // then
        assertThat(getAllPublishers()).isEmpty();
    }

    @Test
    public void createPublishersAndFindThemAll() {
        List<CreatePublisherDto> createPublisherRequests = List.of(
                new CreatePublisherDto("ALPHA", "Address: Berlin, Alexander platz, 1. Tel: 012345"),
                new CreatePublisherDto("BETA", "Address: Frankfurt, Berliner str, 2. Tel: 023456"),
                new CreatePublisherDto("GAMMA", "Address: Dresden, Frankfurter ring, 3. Tel: 034567")
        );
        createPublisherRequests.forEach(this::createPublisher);

        //when
        List<PublisherDto> result = getAllPublishers();

        //then
        assertThat(result).hasSize(createPublisherRequests.size());
        assertThat(result).extracting(PublisherDto::getName).containsExactlyElementsOf(
                createPublisherRequests.stream().map(CreatePublisherDto::getName).collect(toList())
        );
    }


    private long createPublisher(CreatePublisherDto request) {
        ResponseEntity<PublisherDto> response = post("publishers", request, PublisherDto.class);
        String path = response.getHeaders().getLocation().getPath();
        return Long.parseLong(path.substring(path.lastIndexOf("/") + 1)); // id
    }

    private PublisherDto getPublisherById(long id) {
        return get("publishers/" + id, PublisherDto.class);
    }

    private List<PublisherDto> getAllPublishers() {
        return getAll("publishers", new ParameterizedTypeReference<DataDto<PublisherDto>>() {}).getData();
    }

    private void updatePublisher(long id, UpdatePublisherDto request) {
        put("publishers/" + id, request);
    }

    private void deletePublisher(long id) {
        delete("publishers/" + id);
    }
}
