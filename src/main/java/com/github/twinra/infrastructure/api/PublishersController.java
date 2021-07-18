package com.github.twinra.infrastructure.api;

import com.github.twinra.domain.model.Publisher;
import com.github.twinra.domain.ports.provided.PublishersModifier;
import com.github.twinra.domain.ports.provided.PublishersReader;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
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
    private final PublishersReader reader;
    private final PublishersModifier modifier;
    private final PublisherDtoValidator validator;

    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(validator);
    }

    @GetMapping
    public List<PublisherDto> getAll() {
        return reader.getAll().stream().map(PublisherDto::fromDomainObject).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDto> getById(@PathVariable long id) {
        return reader.getById(new Publisher.Id(id))
                .map(PublisherDto::fromDomainObject)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody PublisherDto dto) {
        Publisher.Id savedId = modifier.create(dto.toDomainObject());
        URI savedURI = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + savedId.getValue()).build().toUri();
        return ResponseEntity.created(savedURI).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @Valid @RequestBody PublisherDto dto) {
        boolean updated = modifier.update(new Publisher.Id(id), dto.toDomainObject());
        return updated
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        boolean deleted = modifier.deleteById(new Publisher.Id(id));
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
