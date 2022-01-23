package com.github.twinra.infrastructure.api.dto;

import com.github.twinra.domain.model.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreatePublisherDto {
    @NotNull
    private String name;
    @NotNull
    private String contacts;

    public Publisher.Create toDomainObject() {
        return new Publisher.Create(name, contacts);
    }
}
