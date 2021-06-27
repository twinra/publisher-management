package com.github.twinra.api;

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
        return new Publisher(this.id, this.name, this.email);
    }

    public Publisher toDomainObject(long id) {
        return new Publisher(id, this.name, this.email);
    }

    public static PublisherDto fromDomainObject(Publisher publisher) {
        return new PublisherDto(publisher.getId(), publisher.getName(), publisher.getEmail());
    }
}
