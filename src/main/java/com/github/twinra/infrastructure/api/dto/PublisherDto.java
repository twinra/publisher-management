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
    private Publisher.Status status;

    public static PublisherDto fromDomainObject(Publisher publisher) {
        return PublisherDto.builder()
                .id(publisher.getId().getValue())
                .name(publisher.getName())
                .contacts(publisher.getContacts())
                .status(publisher.getStatus())
                .build();
    }
}
