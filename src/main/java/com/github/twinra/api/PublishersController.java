package com.github.twinra.api;

import com.github.twinra.domain.model.Publisher;
import com.github.twinra.domain.services.PublishersService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/publishers", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Publishers")
public class PublishersController {
    private final PublishersService service;
    private final PublisherDtoValidator validator;

    @InitBinder
    protected void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(validator);
    }

    @GetMapping
    public List<PublisherDto> getAll() {
        return service.getAll().stream().map(PublisherDto::fromDomainObject).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDto> getById(@PathVariable long id) {
        return service.getById(id)
                .map(PublisherDto::fromDomainObject)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody PublisherDto dto) {
        Publisher saved = service.save(dto.toDomainObject());
        URI savedURI = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + saved.getId()).build().toUri();
        return ResponseEntity.created(savedURI).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @Valid @RequestBody PublisherDto dto) {
        Optional<Publisher> found = service.getById(id);
        if(found.isEmpty())
            return ResponseEntity.notFound().build();
        else {
            service.save(dto.toDomainObject(id));
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        Optional<Publisher> found = service.getById(id);
        if(found.isEmpty())
            return ResponseEntity.notFound().build();
        else {
            service.delete(found.get());
            return ResponseEntity.noContent().build();
        }
    }
}
