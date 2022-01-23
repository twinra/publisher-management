package com.github.twinra.infrastructure.api.dto;

import com.github.twinra.domain.model.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PublisherDto {
    private Long id;
    private String name;
    private String contacts;

    public static PublisherDto fromDomainObject(Publisher publisher) {
        return new PublisherDto(publisher.getId().getValue(), publisher.getName(), publisher.getContacts());
    }
}
