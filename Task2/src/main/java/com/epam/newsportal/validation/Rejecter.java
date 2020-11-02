package com.epam.newsportal.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpSession;
import java.util.Locale;

@Component
public class Rejecter {
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;
    @Autowired
    private HttpSession httpSession;
    @Value("{locale.parameter}")
    private String localeParameter;
    @Value("{locale.default}")
    private String localeDefault;

    public void reject(Errors errors, String field, String code, String message, Object[] parameters) {
        errors.rejectValue(field, code, messageSource.getMessage(message, parameters, (
                httpSession.getAttribute(localeParameter) != null
                        ? Locale.forLanguageTag(String.valueOf(httpSession.getAttribute(localeParameter)))
                        : Locale.forLanguageTag(localeDefault))));
    }

}
