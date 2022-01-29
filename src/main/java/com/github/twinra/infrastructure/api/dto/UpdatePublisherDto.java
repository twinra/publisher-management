package com.github.twinra.infrastructure.api.dto;

import com.github.twinra.domain.model.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdatePublisherDto {
    @NotNull
    private String contacts;
    @NotNull
    private Publisher.Status status;

    public Publisher.Update toDomainObject() {
        return new Publisher.Update(contacts, status);
    }
}
