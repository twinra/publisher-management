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
    private String email;

    public Publisher.CreateRequest toDomainObject() {
        return new Publisher.CreateRequest(name, email);
    }
}
