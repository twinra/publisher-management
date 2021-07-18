package com.github.twinra.infrastructure.api;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PublisherDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PublisherDto.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PublisherDto dto = (PublisherDto) o;

        if (!isValidEmail(dto.getEmail()))
            errors.rejectValue("email", "INVALID_EMAIL");
    }

    private boolean isValidEmail(String email) {
        //TODO: implement
        return email.contains("@");
    }
}
