package com.strelnik.spring.validator;


import com.strelnik.spring.bean.Software;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class SoftwareValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Software.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Software software = (Software) o;
        if (software.getId() < 0) {
            errors.rejectValue("id", "negative value");
        }
    }
}
