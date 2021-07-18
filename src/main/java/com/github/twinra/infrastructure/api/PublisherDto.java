package com.github.twinra.infrastructure.api;

import com.github.twinra.domain.model.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PublisherDto {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String email;

    public Publisher toDomainObject() {
        return new Publisher(null, name, email);
    }

    public static PublisherDto fromDomainObject(Publisher publisher) {
        return new PublisherDto(publisher.getId().getValue(), publisher.getName(), publisher.getEmail());
    }
}
