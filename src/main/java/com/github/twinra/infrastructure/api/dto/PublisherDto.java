package com.github.twinra.infrastructure.api.dto;

import com.github.twinra.domain.model.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PublisherDto {
    private Long id;
    private String name;
    private String email;

    public static PublisherDto fromDomainObject(Publisher publisher) {
        return new PublisherDto(publisher.getId().getValue(), publisher.getName(), publisher.getEmail());
    }
}
