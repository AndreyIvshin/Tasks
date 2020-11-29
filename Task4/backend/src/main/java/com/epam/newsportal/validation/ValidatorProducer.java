package com.epam.newsportal.validation;

import javax.enterprise.inject.Produces;
import javax.validation.Validation;
import javax.validation.Validator;

public class ValidatorProducer {

    @Produces
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
