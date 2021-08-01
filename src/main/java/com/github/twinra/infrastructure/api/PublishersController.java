package com.github.twinra.infrastructure.api;

import com.github.twinra.domain.action.SearchPublishers;
import com.github.twinra.domain.action.UpdatePublishers;
import com.github.twinra.domain.model.Publisher;
import com.github.twinra.infrastructure.api.dto.CreatePublisherDto;
import com.github.twinra.infrastructure.api.dto.PublisherDto;
import com.github.twinra.infrastructure.api.dto.UpdatePublisherDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/publishers", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Publishers")
public class PublishersController {
    private final SearchPublishers searchPublishers;
    private final UpdatePublishers updatePublishers;

    @GetMapping
    public List<PublisherDto> getAll() {
        return searchPublishers.findAll().stream().map(PublisherDto::fromDomainObject).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDto> getById(@PathVariable long id) {
        return searchPublishers.findById(new Publisher.Id(id))
                .map(PublisherDto::fromDomainObject)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreatePublisherDto dto) {
        Publisher.Id savedId = updatePublishers.create(dto.toDomainObject());
        URI savedURI = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + savedId.getValue()).build().toUri();
        return ResponseEntity.created(savedURI).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @Valid @RequestBody UpdatePublisherDto dto) {
        boolean updated = updatePublishers.update(new Publisher.Id(id), dto.toDomainObject());
        return updated
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        boolean deleted = updatePublishers.delete(new Publisher.Id(id));
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
